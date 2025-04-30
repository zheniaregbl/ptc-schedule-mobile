package com.syndicate.ptkscheduleapp.core.common.util

import com.syndicate.ptkscheduleapp.core.common.util.extension.removeIf
import com.syndicate.ptkscheduleapp.core.data.dto.PairDTO
import com.syndicate.ptkscheduleapp.core.data.mapper.toModel
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

object ScheduleUtil {

    fun groupDailyScheduleBySubgroup(
        dailySchedule: List<PairItem>
    ): List<List<PairItem>> {

        var listSeveralPair = ArrayList<PairItem>()
        val schedule = ArrayList<List<PairItem>>()

        dailySchedule.forEachIndexed { index, pairItem ->

            if (pairItem.subgroupNumber == 0)
                schedule.add(listOf(pairItem))
            else {

                listSeveralPair.add(pairItem)

                if (index != dailySchedule.lastIndex && dailySchedule[index + 1].subgroupNumber == 0
                    || index == dailySchedule.lastIndex && dailySchedule.isNotEmpty()
                    || index != dailySchedule.lastIndex && dailySchedule[index + 1].pairNumber != pairItem.pairNumber
                ) {
                    schedule.add(listSeveralPair)
                    listSeveralPair = ArrayList()
                }
            }
        }

        return schedule
    }

    fun scheduleWithReplacement(
        currentSchedule: List<List<PairItem>>,
        replacementItem: ReplacementItem?,
        teacherName: String? = null
    ): List<List<PairItem>> {

        if (replacementItem == null)
            return currentSchedule

        val newSchedule = currentSchedule.toMutableList()

        if (teacherName == null) {
            swapReplacement(newSchedule, replacementItem)
            subgroupReplacement(newSchedule, replacementItem)
            ordinaryReplacement(newSchedule, replacementItem)
        } else {
            teacherReplacement(newSchedule, replacementItem, teacherName)
        }

        return newSchedule
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private fun teacherReplacement(
        schedule: MutableList<List<PairItem>>,
        replacementItem: ReplacementItem,
        teacherName: String
    ) {

        val newPairList = ArrayList<List<PairItem>>()

        val changePairMap = hashMapOf<String, List<Int>>()
        val changePairList = ArrayList<List<PairItem>>()

        val cancelPairMap = hashMapOf<String, List<Int>>()
        val cancelPairList = ArrayList<List<PairItem>>()

        replacementItem.listReplacement.forEach { replacement ->
            if (replacement.first().previousPairInfo == null) {
                newPairList.add(replacement.map { it.copy(isReplacement = true, isNewPair = true) })
            } else {
                if (replacement.first().previousPairInfo!!.teacher == teacherName) {

                    val group = replacement.first().previousPairInfo!!.group

                    if (replacement.first().teacher == teacherName) {
                        changePairMap[group] = changePairMap[group]?.plus(replacement.first().pairNumber) ?: listOf(replacement.first().pairNumber)
                        changePairList.add(replacement.map { it.copy(isReplacement = true) })
                    } else {
                        cancelPairMap[group] = cancelPairMap[group]?.plus(replacement.first().pairNumber) ?: listOf(replacement.first().pairNumber)
                        cancelPairList.add(
                            replacement.map {
                                it.copy(
                                    isReplacement = true,
                                    subject = if (it.subject.lowercase() != "дистанционное обучение")
                                    "Не будет" else it.subject
                                )
                            }
                        )
                    }
                } else {
                    if (replacement.first().teacher == teacherName) {
                        newPairList.add(replacement.map { it.copy(isReplacement = true, isNewPair = true) })
                    }
                }
            }
        }

        schedule.removeAll { pair -> pair.first().group in changePairMap.keys && changePairMap[pair.first().group]?.contains(pair.first().pairNumber) != false }
        schedule.removeAll { pair -> pair.first().group in cancelPairMap.keys && cancelPairMap[pair.first().group]?.contains(pair.first().pairNumber) != false }

        schedule.addAll(changePairList)
        schedule.addAll(cancelPairList)
        schedule.addAll(newPairList)

        val timeFormatPattern = "HH:mm"
        val timeFormat = LocalTime.Format { byUnicodePattern(timeFormatPattern) }

        schedule.sortBy {
            timeFormat.parse(
                it.first().time
                    .substringBefore("-")
                    .replace(".", ":")
                    .normalizeTime()
            )
        }
    }

    private fun swapReplacement(
        schedule: MutableList<List<PairItem>>,
        replacementItem: ReplacementItem
    ) {

        val swapNumberPairList = ArrayList<Int>()
        val swapPairList = ArrayList<List<PairItem>>()

        replacementItem.listReplacement.forEach { replacement ->
            if (replacement.first().previousPairNumber != -1) {
                swapPairList.add(
                    replacement.map {
                        it.copy(
                            swapPair = true,
                            isReplacement = true
                        )
                    }
                )
                swapNumberPairList.add(replacement.first().previousPairNumber)
            }
        }

        schedule.removeAll { it.first().pairNumber in swapNumberPairList }

        schedule.addAll(swapPairList)

        schedule.sortBy { it.first().pairNumber }
    }

    private fun subgroupReplacement(
        schedule: MutableList<List<PairItem>>,
        replacementItem: ReplacementItem
    ) {

        val replacementNumberPairList = ArrayList<Int>()
        val replacementPairList = ArrayList<List<PairItem>>()
        val newSchedule = ArrayList<List<PairItem>>()

        replacementItem.listReplacement.forEach { replacement ->
            if (replacement.first().subgroupNumber != 0) {
                replacementPairList.add(replacement)
                replacementNumberPairList.add(replacement.first().pairNumber)
            }
        }

        schedule.forEach { pair ->

            if (pair.first().pairNumber in replacementNumberPairList) {

                val replacements = replacementPairList
                    .find { it.first().pairNumber == pair.first().pairNumber }!!
                val replacementSubgroupNumber = replacements.map { it.subgroupNumber }.toSet()
                val tempPair = ArrayList<PairItem>()

                pair.forEach { pairBySubgroup ->
                    if (pairBySubgroup.subgroupNumber in replacementSubgroupNumber) {
                        tempPair.add(
                            replacements
                                .find { it.subgroupNumber == pairBySubgroup.subgroupNumber }!!
                                .copy(isReplacement = true)
                        )
                    } else {
                        tempPair.add(pairBySubgroup)
                    }
                }

                newSchedule.add(tempPair)

            } else {
                newSchedule.add(pair)
            }
        }

        schedule.clear()
        schedule.addAll(newSchedule)
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    private fun ordinaryReplacement(
        schedule: MutableList<List<PairItem>>,
        replacementItem: ReplacementItem
    ) {

        val replacementNumberPairList = ArrayList<Int>()
        val replacementPairList = ArrayList<List<PairItem>>()
        val newSchedule = ArrayList<List<PairItem>>()

        replacementItem.listReplacement.forEach { replacement ->
            if (replacement.first().subgroupNumber == 0) {
                replacementPairList.add(replacement)
                replacementNumberPairList.add(replacement.first().pairNumber)
            }
        }

        schedule.forEach { pair ->

            if (pair.first().pairNumber in replacementNumberPairList) {

                replacementPairList
                    .find { it.first().pairNumber == pair.first().pairNumber }
                    ?.let { pairList ->
                        newSchedule.add(pairList.map { it.copy(isReplacement = true) })
                    }

                replacementPairList.removeIf { removePair ->
                    removePair == replacementPairList
                        .find { it.first().pairNumber == pair.first().pairNumber }
                }

            } else {
                newSchedule.add(pair)
            }
        }

        newSchedule.addAll(
            replacementPairList.map {
                pairList -> pairList.map { it.copy(isReplacement = true, isNewPair = true) }
            }
        )

        val timeFormatPattern = "HH:mm"
        val timeFormat = LocalTime.Format { byUnicodePattern(timeFormatPattern) }

        newSchedule.sortBy {
            timeFormat.parse(
                it.first().time
                    .substringBefore("-")
                    .replace(".", ":")
                    .normalizeTime()
            )
        }

        schedule.clear()
        schedule.addAll(newSchedule)
    }

    private fun String.normalizeTime(): String {
        return if (this.substringBefore(":").length > 1)
            this
        else
            "0$this"
    }

    fun getWeekScheduleByWeekType(
        inputSchedule: List<List<PairItem>>,
        isUpperWeek: Boolean
    ): List<List<PairItem>> {

        val schedule = ArrayList<List<PairItem>>()

        inputSchedule.forEach { daySchedule ->
            schedule.add(filterScheduleByWeekType(daySchedule, isUpperWeek))
        }

        return schedule
    }

    private fun getDayOfWeek(number: Int): String {
        return when (number) {
            0 -> "понедельник"
            1 -> "вторник"
            2 -> "среда"
            3 -> "четверг"
            4 -> "пятница"
            5 -> "суббота"
            else -> "воскресенье"
        }
    }

    fun getWeekSchedule(listPair: List<PairItem>): List<List<PairItem>> {

        if (listPair.isEmpty())
            return emptyList()

        val schedule = mutableListOf<List<PairItem>>()

        (0..6).forEach { dayNumber ->
            val dailyPair = listPair.filter { pairItem ->
                pairItem.dayOfWeek.lowercase() == getDayOfWeek(dayNumber)
            }
            schedule.add(dailyPair)
        }

        return schedule
    }

    private fun filterScheduleByWeekType(
        weekSchedule: List<PairItem>,
        isUpperWeek: Boolean
    ): List<PairItem> {

        if (weekSchedule.isEmpty())
            return emptyList()

        val schedule = ArrayList<PairItem>()

        weekSchedule.forEach {
            if (it.isUpper == null || it.isUpper == isUpperWeek)
                schedule.add(it)
        }

        schedule.sortBy { it.pairNumber }

        return schedule
    }

    fun getWeeksFromStartDate(
        startDate: LocalDate,
        weeksCount: Int
    ): List<List<LocalDate>> {
        val weeks = mutableListOf<List<LocalDate>>()
        var currentStartOfWeek = startDate

        while (currentStartOfWeek.dayOfWeek != DayOfWeek.MONDAY) {
            currentStartOfWeek = currentStartOfWeek.minus(1, DateTimeUnit.DAY)
        }

        repeat(weeksCount) {
            val week = (0 until 7)
                .map { currentStartOfWeek.plus(it, DateTimeUnit.DAY) }
            weeks.add(week)
            currentStartOfWeek = currentStartOfWeek.plus(1, DateTimeUnit.WEEK)
        }

        return weeks
    }

    fun getCurrentWeek(weeks: List<List<LocalDate>>, currentDate: LocalDate): Int {
        for (i in weeks.indices) {
            for (j in weeks[i].indices) {
                if (weeks[i][j] == currentDate) {
                    return i
                }
            }
        }

        return 0
    }

    fun getCurrentTypeWeek(
        typeWeekNow: Boolean,
        prevPage: Int,
        currentPage: Int
    ) = if (prevPage % 2 == currentPage % 2) typeWeekNow else !typeWeekNow

    fun getReplacementFromJsonForStudent(
        jsonReplacement: JsonObject,
        group: String
    ): List<ReplacementItem> {

        if (jsonReplacement.toString() == "")
            return emptyList()

        val listReplacement = ArrayList<ReplacementItem>()
        val listDate = jsonObjectToMap(jsonReplacement).keys.toList()
        val formatter = LocalDate.Format {
            dayOfMonth()
            char('.')
            monthNumber()
            char('.')
            year()
        }

        val parser = Json { ignoreUnknownKeys = true }

        listDate.forEach { date ->

            var dailyReplacementList = ArrayList<List<PairItem>>()
            var pairReplacement = ArrayList<PairItem>()
            var lastPairNumber = 0

            try {

                val dailyReplacementJson = jsonReplacement[date]!!.jsonObject[group]!!.jsonArray

                for (jsonElement in dailyReplacementJson) {

                    val pairItem = parser
                        .decodeFromJsonElement<PairDTO>(jsonElement)
                        .toModel()

                    if (pairItem.pairNumber == -1) {
                        dailyReplacementList.add(listOf(pairItem))
                        continue
                    }

                    if (lastPairNumber == 0) {
                        lastPairNumber = pairItem.pairNumber
                    }

                    if (lastPairNumber != pairItem.pairNumber) {
                        dailyReplacementList.add(pairReplacement)
                        lastPairNumber = pairItem.pairNumber
                        pairReplacement = ArrayList()
                    }

                    lastPairNumber = pairItem.pairNumber
                    pairReplacement.add(pairItem)
                }

                if (pairReplacement.isNotEmpty()) {
                    dailyReplacementList.add(pairReplacement)
                }

            } catch (e: Exception) {
                println(e)
            }

            if (dailyReplacementList.isNotEmpty()) {

                listReplacement.add(
                    ReplacementItem(
                        date = formatter.parse(date),
                        listReplacement = dailyReplacementList
                    )
                )

                dailyReplacementList = ArrayList()
            }
        }

        return listReplacement
    }

    fun getReplacementFromJsonForTeacher(
        jsonReplacement: JsonObject,
        teacherName: String
    ): List<ReplacementItem> {

        if (jsonReplacement.toString() == "")
            return emptyList()

        val listReplacement = ArrayList<ReplacementItem>()
        val listDate = jsonObjectToMap(jsonReplacement).keys.toList()
        val formatter = LocalDate.Format {
            dayOfMonth()
            char('.')
            monthNumber()
            char('.')
            year()
        }

        val parser = Json { ignoreUnknownKeys = true }

        listDate.forEach { date ->

            var dailyReplacementList = ArrayList<List<PairItem>>()
            var pairReplacement = ArrayList<PairItem>()
            var lastPairNumber = 0

            val listGroupByDate = jsonObjectToMapArrayValues(jsonReplacement[date]!!.jsonObject).keys.toList()

            listGroupByDate.forEach { group ->

                val dailyReplacementJson = jsonReplacement[date]!!.jsonObject[group]!!.jsonArray

                try {

                    for (jsonElement in dailyReplacementJson) {

                        if (group == "-") continue

                        val pairItem = parser
                            .decodeFromJsonElement<PairDTO>(jsonElement)
                            .toModel()

                        if (pairItem.previousPairInfo?.teacher != teacherName
                            && pairItem.teacher != teacherName) continue

                        if (pairItem.pairNumber == -1) {
                            dailyReplacementList.add(listOf(pairItem))
                            continue
                        }

                        if (lastPairNumber == 0) {
                            lastPairNumber = pairItem.pairNumber
                        }

                        if (lastPairNumber != pairItem.pairNumber) {
                            if (pairReplacement.isEmpty()) {
                                pairReplacement.add(pairItem)
                                continue
                            }
                            dailyReplacementList.add(pairReplacement)
                            lastPairNumber = pairItem.pairNumber
                            pairReplacement = ArrayList()
                            pairReplacement.add(pairItem)
                            continue
                        }

                        lastPairNumber = pairItem.pairNumber
                        pairReplacement.add(pairItem)
                    }

                } catch (e: Exception) {
                    println(e)
                }

                if (pairReplacement.isNotEmpty()) {
                    dailyReplacementList.add(pairReplacement)
                    pairReplacement = ArrayList()
                }

                lastPairNumber = 0
            }

            if (dailyReplacementList.isNotEmpty()) {

                listReplacement.add(
                    ReplacementItem(
                        date = formatter.parse(date),
                        listReplacement = dailyReplacementList
                    )
                )

                dailyReplacementList = ArrayList()
            }
        }

        return listReplacement
    }

    private fun jsonObjectToMap(jsonObject: JsonObject): Map<String, JsonObject> {
        return jsonObject.mapValues { it.value.jsonObject }
    }

    private fun jsonObjectToMapArrayValues(jsonObject: JsonObject): Map<String, JsonArray> {
        return jsonObject.mapValues { it.value.jsonArray }
    }
}