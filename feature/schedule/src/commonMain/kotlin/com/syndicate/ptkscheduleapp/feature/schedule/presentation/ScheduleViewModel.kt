package com.syndicate.ptkscheduleapp.feature.schedule.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.core.domain.repository.PreferencesRepository
import com.syndicate.ptkscheduleapp.core.common.util.ScheduleUtil
import com.syndicate.ptkscheduleapp.core.common.util.extension.nowDate
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.core.domain.model.ScheduleInfo
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.CaseResult
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetLocalReplacementCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetLocalScheduleCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetLocalWeekTypeCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetReplacementCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetScheduleCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetScheduleInfoCase
import com.syndicate.ptkscheduleapp.feature.schedule.domain.use_case.GetWeekTypeBySelectedDateCase
import com.syndicate.ptkscheduleapp.feature.schedule.platformConnectivity
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
import kotlinx.datetime.Month

internal class ScheduleViewModel(
    private val preferencesRepository: PreferencesRepository,
    private val getScheduleInfoCase: GetScheduleInfoCase,
    private val getReplacementCase: GetReplacementCase,
    private val getScheduleCase: GetScheduleCase,
    private val getLocalWeekTypeCase: GetLocalWeekTypeCase,
    private val getLocalReplacementCase: GetLocalReplacementCase,
    private val getLocalScheduleCase: GetLocalScheduleCase,
    private val getWeekTypeBySelectedDateCase: GetWeekTypeBySelectedDateCase
) : ViewModel() {

    private val _state = MutableStateFlow(ScheduleState())
    val state = _state
        .onStart { onAction(ScheduleAction.OnUpdateScheduleInfo) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(30_000L),
            _state.value
        )

    private val connectivity = platformConnectivity()

    private val _scheduleInfo: MutableStateFlow<ScheduleInfo?> = MutableStateFlow(null)

    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _initWeekType = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            connectivity.statusUpdates.collect { status ->
                _state.update {

                    if (it.isConnected != status.isConnected && status.isConnected)
                        onAction(ScheduleAction.OnUpdateScheduleInfo)

                    it.copy(isConnected = status.isConnected)
                }
            }
        }
    }

    fun onAction(action: ScheduleAction) {

        when (action) {

            is ScheduleAction.OnChangeSchedulePage ->
                _state.update { it.copy(selectedSchedulePage = action.page) }

            is ScheduleAction.OnChangeSelectedDate ->
                _state.update { it.copy(selectedDate = action.date) }

            is ScheduleAction.OnUpdateDailyWeekState -> {

                val weekNumber = ScheduleUtil
                    .getCurrentWeek(weeks, action.currentDate)

                _state.update {
                    it.copy(
                        selectedDateWeekType = if (weekNumber % 2 == currentWeekNumber % 2)
                            _initWeekType.value else !_initWeekType.value
                    )
                }
            }

            ScheduleAction.OnUpdateScheduleInfo -> {
                viewModelScope.launch {
                    _errorMessage.emit(null)
                    getUserGroup()
                    getScheduleInfo()
                    getReplacement()
                    getSchedule()
                }
            }

            is ScheduleAction.OnChangeTheme ->
                viewModelScope.launch { preferencesRepository.saveThemeMode(action.themeMode) }

            else -> Unit
        }
    }

    private suspend fun getUserGroup() =
        _state.update { it.copy(currentGroupNumber = preferencesRepository.getUserGroup()) }

    private suspend fun getScheduleInfo() {

        _state.update { it.copy(isLoading = true) }

        when (val result = getScheduleInfoCase()) {

            is CaseResult.Error -> {

                _errorMessage.emit(result.message)

                val localWeekType = getLocalWeekTypeCase()

                if (_state.value.isUpperWeek == null) {
                    _initWeekType.update { localWeekType }
                    _state.update { it.copy(selectedDateWeekType = localWeekType) }
                } else {
                    _state.update { it.copy(
                        selectedDateWeekType = getWeekTypeBySelectedDateCase(
                            localWeekType,
                            currentWeekNumber,
                            _state.value.selectedSchedulePage
                        )
                    ) }
                }

                _state.update { it.copy(isUpperWeek = getLocalWeekTypeCase()) }
            }

            is CaseResult.Success<ScheduleInfo> -> {

                if (_state.value.isUpperWeek == null) {
                    _initWeekType.update { result.data.isUpperWeek!! }
                    _state.update { it.copy(selectedDateWeekType = result.data.isUpperWeek!!) }
                } else {
                    _state.update { it.copy(
                        selectedDateWeekType = getWeekTypeBySelectedDateCase(
                            result.data.isUpperWeek!!,
                            currentWeekNumber,
                            _state.value.selectedSchedulePage
                        )
                    ) }
                }

                _state.update { it.copy(isUpperWeek = result.data.isUpperWeek) }
                _scheduleInfo.update { result.data }

                preferencesRepository.saveLocalWeekType(result.data.isUpperWeek!!)
            }
        }
    }

    private suspend fun getReplacement() {

        val userGroup = _state.value.currentGroupNumber

        val lastUpdateTime = _scheduleInfo.value?.lastReplacementUpdateTime

        when (val result = getReplacementCase(userGroup, lastUpdateTime)) {

            is CaseResult.Error -> {
                _errorMessage.emit(result.message)
                getLocalReplacementCase(userGroup)?.let { replacement ->
                    _state.update { it.copy(replacement = replacement) }
                }
            }

            is CaseResult.Success<List<ReplacementItem>> -> {
                _state.update { it.copy(replacement = result.data) }
            }
        }
    }

    private suspend fun getSchedule() {

        val userGroup = _state.value.currentGroupNumber

        val lastUpdateTime = _scheduleInfo
            .value
            ?.groupInfo
            ?.find { it.group == userGroup }
            ?.lastUpdateTime

        when (val result = getScheduleCase(userGroup, lastUpdateTime)) {

            is CaseResult.Error -> {
                _errorMessage.emit(result.message)
                getLocalScheduleCase()?.let { schedule ->
                    _state.update { it.copy(
                        isLoading = false,
                        schedule = schedule
                    ) }
                }
            }

            is CaseResult.Success<List<List<PairItem>>> -> {
                _state.update { it.copy(
                    isLoading = false,
                    schedule = result.data
                ) }
            }
        }
    }

    private companion object {

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