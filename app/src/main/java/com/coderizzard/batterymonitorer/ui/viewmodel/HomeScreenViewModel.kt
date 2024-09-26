package com.coderizzard.batterymonitorer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coderizzard.batterymonitorer.db.dao.BtPercentageDao
import com.coderizzard.batterymonitorer.db.entity.BtPercentage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val dao: BtPercentageDao
) : ViewModel() {

//    private var _latestPercentageInfo = MutableStateFlow()

    val latestPercentageInfo = dao.getAll()

//    private fun refetchLatestPercentage() {
//        viewModelScope.launch {
//            _latestPercentageInfo.update {
//                dao.getAll()
//            }
//        }
//    }

    fun onEvent(event : HomeScreenEvent) {
        when(event ) {
            is HomeScreenEvent.RefetchLatestPercentage -> {
//                refetchLatestPercentage()
            }
        }
    }





}

sealed interface HomeScreenEvent {
    object RefetchLatestPercentage : HomeScreenEvent
}