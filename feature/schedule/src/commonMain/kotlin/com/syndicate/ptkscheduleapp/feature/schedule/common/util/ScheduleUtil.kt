package com.syndicate.ptkscheduleapp.feature.schedule.common.util

import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.PairItem
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.ReplacementItem
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

internal object ScheduleUtil {

    fun getReplacementFromJson(
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

        listDate.forEach { date ->

            var dailyReplacementList = ArrayList<List<PairItem>>()
            var pairReplacement = ArrayList<PairItem>()
            var lastPairNumber = 0

            try {
                val dailyReplacementJson = jsonReplacement[date]?.jsonObject?.get(group)?.jsonArray

                if (dailyReplacementJson != null) {
                    for (jsonElement in dailyReplacementJson) {
                        val replacementItemJson = jsonElement.jsonObject
                        val pairItem = PairItem(
                            dayOfWeek = replacementItemJson["dayOfWeek"]?.jsonPrimitive?.content ?: "",
                            isUpper = replacementItemJson["isUpper"]?.jsonPrimitive?.boolean ?: false,
                            pairNumber = replacementItemJson["pairNumber"]?.jsonPrimitive?.int ?: -1,
                            subject = replacementItemJson["subject"]?.jsonPrimitive?.content ?: "",
                            place = replacementItemJson["place"]?.jsonPrimitive?.content ?: "",
                            room = replacementItemJson["cabinet"]?.jsonPrimitive?.content ?: "",
                            teacher = replacementItemJson["teacher"]?.jsonPrimitive?.content ?: "",
                            subgroupNumber = replacementItemJson["subgroupNumber"]?.jsonPrimitive?.int ?: -1,
                            time = replacementItemJson["time"]?.jsonPrimitive?.content ?: "",
                            previousPairNumber = replacementItemJson["previousPairNumber"]?.jsonPrimitive?.int ?: -1
                        )

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
                }
            } catch (_: Exception) {

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
}