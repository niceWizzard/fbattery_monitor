package com.coderizzard.batterymonitorer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.coderizzard.batterymonitorer.db.dao.RespondentDao
import com.coderizzard.batterymonitorer.db.entity.Respondent

class RespondentStateViewModel(
    private var _respondent: Respondent?
) : ViewModel() {
    val respondent = _respondent

    fun update(respondent: Respondent) {
        _respondent = respondent
    }

}