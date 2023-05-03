package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShareParkingLotViewModelFactory (
    private val app : Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShareParkingLotViewModel(app) as T
    }
}