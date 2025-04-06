package com.syndicate.ptkscheduleapp.widget.presentation

import androidx.lifecycle.ViewModel
import com.syndicate.ptkscheduleapp.core.domain.model.PairItem
import com.syndicate.ptkscheduleapp.core.domain.model.ReplacementItem
import com.syndicate.ptkscheduleapp.widget.domain.use_case.GetDailyScheduleCase
import com.syndicate.ptkscheduleapp.widget.domain.use_case.GetReplacementCase
import com.syndicate.ptkscheduleapp.widget.domain.use_case.GetScheduleCase
import com.syndicate.ptkscheduleapp.widget.domain.use_case.GetWeekTypeCase
import com.syndicate.ptkscheduleapp.widget.domain.use_case.SaveWidgetData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class WidgetViewModel(
    private val getWeekTypeCase: GetWeekTypeCase,
    private val getReplacementCase: GetReplacementCase,
    private val getScheduleCase: GetScheduleCase,
    private val getDailyScheduleCase: GetDailyScheduleCase,
    private val saveWidgetData: SaveWidgetData
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

        saveWidgetData(getDailySchedule())
    }

    private fun getDailySchedule(): List<List<PairItem>> {
        return getDailyScheduleCase(
            _weekType.value,
            _schedule.value,
            _replacement.value
        )
    }

    private suspend fun getScheduleInfo() { _weekType.update { getWeekTypeCase() } }

    private suspend fun getReplacement() {
        getReplacementCase()?.let { replacement ->
            _replacement.update { replacement }
        }
    }

    private suspend fun getSchedule() {
        getScheduleCase()?.let { schedule ->
            _schedule.update { schedule }
        }
    }
}