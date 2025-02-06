package com.syndicate.ptkscheduleapp.feature.schedule.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.feature.schedule.common.util.extension.nowDate
import com.syndicate.ptkscheduleapp.feature.schedule.data.dto.PairDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.mapper.toDTO
import com.syndicate.ptkscheduleapp.feature.schedule.data.mapper.toModel
import com.syndicate.ptkscheduleapp.feature.schedule.domain.model.ScheduleInfo
import com.syndicate.ptkscheduleapp.feature.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

internal class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ScheduleState())
    val state = _state
        .onStart {
            viewModelScope.launch {
                getUserGroup()
                getScheduleInfo()
                getReplacement()
                getSchedule()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(10_000L),
            _state.value
        )

    private val _scheduleInfo: MutableStateFlow<ScheduleInfo?> = MutableStateFlow(null)

    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _initWeekType = MutableStateFlow(false)

    fun onAction(action: ScheduleAction) {

        when (action) {

            is ScheduleAction.ChangeSchedulePage ->
                _state.update { it.copy(selectedSchedulePage = action.page) }

            is ScheduleAction.ChangeSelectedDate ->
                _state.update { it.copy(selectedDate = action.date) }

            is ScheduleAction.UpdateDailyWeekState -> {

                val weekNumber = ScheduleUtil
                    .getCurrentWeek(weeks, action.currentDate)

                _state.update {
                    it.copy(
                        selectedDateWeekType = if (weekNumber % 2 == currentWeekNumber % 2)
                            _initWeekType.value else !_initWeekType.value
                    )
                }
            }

            ScheduleAction.UpdateScheduleInfo -> {
                viewModelScope.launch {
                    _errorMessage.emit(null)
                    getScheduleInfo()
                    getReplacement()
                    getSchedule()
                }
            }
        }
    }

    private suspend fun getUserGroup() =
        _state.update { it.copy(currentGroupNumber = preferencesRepository.getUserGroup()) }

    private suspend fun getScheduleInfo() {

        _state.update { it.copy(isLoading = true) }

        scheduleRepository
            .getScheduleInfo()
            .suspendOnSuccess {
                if (_state.value.isUpperWeek == null) {
                    _initWeekType.update { data.isUpperWeek!! }
                    _state.update { it.copy(selectedDateWeekType = data.isUpperWeek!!) }
                } else {

                    val startWeekType = ScheduleUtil
                        .getCurrentTypeWeek(data.isUpperWeek!!, currentWeekNumber, 0)
                    val currentWeekType = if (_state.value.selectedSchedulePage / 7 % 2 == 0)
                        startWeekType else !startWeekType

                    _state.update { it.copy(selectedDateWeekType = currentWeekType) }
                }

                _state.update { it.copy(isUpperWeek = data.isUpperWeek) }
                _scheduleInfo.update { data }
            }
            .suspendOnError {
                _errorMessage.emit("Error getScheduleInfo")
            }
            .suspendOnException {
                _errorMessage.emit("Exception getScheduleInfo")
            }
    }

    private suspend fun getReplacement() {

        val userGroup = _state.value.currentGroupNumber

        scheduleRepository
            .getReplacement()
            .suspendOnSuccess {

                _scheduleInfo.value?.let { scheduleInfo ->

                    val lastUpdateTime = scheduleInfo
                        .lastReplacementUpdateTime

                    if (preferencesRepository.getLastUpdateReplacementTime() != lastUpdateTime) {

                        preferencesRepository.saveLocalReplacement(data.toString())

                        _errorMessage.emit("Success getReplacement")

                        preferencesRepository.saveLastUpdateReplacementTime(lastUpdateTime)
                    }
                }

                _state.update {
                    it.copy(
                        replacement = ScheduleUtil
                            .getReplacementFromJson(data, userGroup)
                    )
                }
            }
            .suspendOnError {

                _errorMessage.emit("Error getReplacement")

                preferencesRepository.getLocalReplacement()?.let { replacement ->
                    _state.update {
                        it.copy(
                            replacement = ScheduleUtil
                                .getReplacementFromJson(
                                    Json.decodeFromString<JsonObject>(replacement),
                                    userGroup
                                )
                        )
                    }
                }
            }
            .suspendOnException {

                _errorMessage.emit("Exception getReplacement")

                preferencesRepository.getLocalReplacement()?.let { replacement ->
                    _state.update {
                        it.copy(
                            replacement = ScheduleUtil
                                .getReplacementFromJson(
                                    Json.decodeFromString<JsonObject>(replacement),
                                    userGroup
                                )
                        )
                    }
                }
            }
    }

    private suspend fun getSchedule() {

        val userGroup = _state.value.currentGroupNumber

        scheduleRepository
            .getSchedule(userGroup)
            .suspendOnSuccess {

                _scheduleInfo.value?.let { scheduleInfo ->

                    val lastUpdateTime = scheduleInfo
                        .groupInfo
                        .find { it.group == userGroup }
                        ?.lastUpdateTime

                    if (preferencesRepository.getLastUpdateScheduleTime() != lastUpdateTime) {

                        preferencesRepository
                            .saveLocalSchedule(
                                Json.encodeToString(data.map { it.toDTO() })
                            )

                        _errorMessage.emit("Success getSchedule")

                        lastUpdateTime?.let {
                            preferencesRepository.saveLastUpdateScheduleTime(it)
                        }
                    }
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        schedule = ScheduleUtil
                            .getWeekSchedule(data)
                    )
                }
            }
            .suspendOnError {

                _state.update { it.copy(isLoading = false) }

                _errorMessage.emit("Error getSchedule")

                preferencesRepository.getLocalSchedule()?.let { scheduleString ->

                    val localSchedule = Json
                        .decodeFromString<List<PairDTO>>(scheduleString)
                        .map { it.toModel() }

                    _state.update {
                        it.copy(
                            schedule = ScheduleUtil
                                .getWeekSchedule(localSchedule)
                        )
                    }
                }
            }
            .suspendOnException {

                _state.update { it.copy(isLoading = false) }

                _errorMessage.emit("Exception getSchedule")

                preferencesRepository.getLocalSchedule()?.let { scheduleString ->

                    val localSchedule = Json
                        .decodeFromString<List<PairDTO>>(scheduleString)
                        .map { it.toModel() }

                    _state.update {
                        it.copy(
                            schedule = ScheduleUtil
                                .getWeekSchedule(localSchedule)
                        )
                    }
                }
            }
    }

    companion object {

        val weeks = ScheduleUtil.getWeeksFromStartDate(
            LocalDate(Clock.System.nowDate().year, Month.JANUARY, 1),
            78
        )
        val currentWeekNumber = ScheduleUtil.getCurrentWeek(
            weeks,
            Clock.System.nowDate()
        )
    }
}