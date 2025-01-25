package com.syndicate.ptkscheduleapp.feature.schedule.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syndicate.ptkscheduleapp.feature.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.launch

internal class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository
): ViewModel() {


    init { viewModelScope.launch { scheduleRepository.getSchedule("1991") } }
}