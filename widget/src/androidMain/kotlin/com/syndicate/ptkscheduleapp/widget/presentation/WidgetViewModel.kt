package com.syndicate.ptkscheduleapp.widget.presentation

import androidx.lifecycle.ViewModel
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.common.util.extension.nowDate
import com.syndicate.ptkscheduleapp.core.data.dto.PairDTO
import com.syndicate.ptkscheduleapp.core.data.mapper.toLocalDTO
import com.syndicate.ptkscheduleapp.core.data.mapper.toModel
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.domain.repository.ScheduleRepository
import com.syndicate.ptkscheduleapp.core.common.util.extension.shortName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.time.format.DateTimeFormatter

internal class WidgetViewModel(
    private val scheduleRepository: ScheduleRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _weekType: MutableStateFlow<Boolean?>  = MutableStateFlow(null)
    private val _replacement: MutableStateFlow<List<ReplacementItem>> = MutableStateFlow(emptyList())
    private val _schedule: MutableStateFlow<List<List<PairItem>>> = MutableStateFlow(emptyList())

    suspend fun onAction(action: WidgetAction) {
        when (action) {
            WidgetAction.UpdateWidgetSchedule -> updateWidgetSchedule()
        }
    }

    private suspend fun updateWidgetSchedule() {

        getScheduleInfo()
        getReplacement()
        getSchedule()

        val schedule = getDailySchedule()

        val currentDayOfWeek = Clock.System.nowDate().dayOfWeek.shortName
        val dtf = DateTimeFormatter.ofPattern("HH:mm")
        val currentTime = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .toJavaLocalDateTime()
            .format(dtf)

        preferencesRepository
            .saveWidgetSchedule(
                Json.encodeToString(schedule.map { weekSchedule -> weekSchedule.map { it.toLocalDTO() } })
            )

        preferencesRepository.saveLastUpdateWidgetTime("$currentDayOfWeek, $currentTime")
    }

    private fun getDailySchedule(): List<List<PairItem>> {

        val weeks = ScheduleUtil.getWeeksFromStartDate(
            LocalDate(Clock.System.nowDate().year, Month.JANUARY, 1),
            78
        )
        val currentWeekNumber = ScheduleUtil.getCurrentWeek(
            weeks,
            Clock.System.nowDate()
        )

        val scheduleList = listOf(
            ScheduleUtil.getWeekScheduleByWeekType(_schedule.value, true),
            ScheduleUtil.getWeekScheduleByWeekType(_schedule.value, false)
        )
        val startScheduleIndex = if (
            ScheduleUtil.getCurrentTypeWeek(
                _weekType.value == true, currentWeekNumber, 0
            )
        ) 0 else 1

        val dayNumber = (currentWeekNumber * 7) + weeks[currentWeekNumber].indexOf(Clock.System.nowDate())

        val currentScheduleIndex = if (dayNumber / 7 % 2 == 0) startScheduleIndex
        else 1 - startScheduleIndex

        val dailySchedule: List<PairItem> = try {
            scheduleList[currentScheduleIndex][dayNumber % 7]
        } catch (_: Exception) {
            emptyList()
        }

        val dailyReplacement =
            _replacement.value.find { it.date == Clock.System.nowDate() }

        return ScheduleUtil.scheduleWithReplacement(
            ScheduleUtil.groupDailyScheduleBySubgroup(dailySchedule),
            dailyReplacement
        )
    }

    private suspend fun getScheduleInfo() {
        scheduleRepository
            .getScheduleInfo()
            .suspendOnSuccess {
                _weekType.update { data.isUpperWeek }
                preferencesRepository.saveLocalWeekType(data.isUpperWeek!!)
            }
            .suspendOnError { _weekType.update { preferencesRepository.getLocalWeekType() } }
            .suspendOnException { _weekType.update { preferencesRepository.getLocalWeekType() } }
    }

    private suspend fun getReplacement() {

        val userGroup = preferencesRepository.getUserGroup()

        scheduleRepository
            .getReplacement()
            .suspendOnSuccess {
                _replacement.update { ScheduleUtil.getReplacementFromJson(data, userGroup) }
                preferencesRepository.saveLocalReplacement(data.toString())
            }
            .suspendOnError {
                preferencesRepository.getLocalReplacement()?.let { replacement ->
                    _replacement.update {
                        ScheduleUtil
                            .getReplacementFromJson(
                                Json.decodeFromString<JsonObject>(replacement),
                                userGroup
                            )
                    }
                }
            }
            .suspendOnException {
                preferencesRepository.getLocalReplacement()?.let { replacement ->
                    _replacement.update {
                        ScheduleUtil
                            .getReplacementFromJson(
                                Json.decodeFromString<JsonObject>(replacement),
                                userGroup
                            )
                    }
                }
            }
    }

    private suspend fun getSchedule() {

        val userGroup = preferencesRepository.getUserGroup()

        scheduleRepository
            .getSchedule(userGroup)
            .suspendOnSuccess { _schedule.update { ScheduleUtil.getWeekSchedule(data) } }
            .suspendOnError {
                preferencesRepository.getLocalSchedule()?.let { scheduleString ->
                    val localSchedule = Json
                        .decodeFromString<List<PairDTO>>(scheduleString)
                        .map { it.toModel() }
                    _schedule.update { ScheduleUtil.getWeekSchedule(localSchedule) }
                }
            }
            .suspendOnException {
                preferencesRepository.getLocalSchedule()?.let { scheduleString ->
                    val localSchedule = Json
                        .decodeFromString<List<PairDTO>>(scheduleString)
                        .map { it.toModel() }
                    _schedule.update { ScheduleUtil.getWeekSchedule(localSchedule) }
                }
            }
    }
}