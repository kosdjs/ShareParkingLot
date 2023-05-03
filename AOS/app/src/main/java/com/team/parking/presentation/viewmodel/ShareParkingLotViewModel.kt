package com.team.parking.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val TAG = "ShareParkingLotViewMode"

class ShareParkingLotViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    private var _uriList: MutableLiveData<MutableList<Uri>> = MutableLiveData(mutableListOf())
    val uriList: LiveData<MutableList<Uri>> get() = _uriList

    fun clearList(){
        _uriList.value?.clear()
    }

    fun addImage(uri: Uri){
        _uriList.value?.add(uri)
        _uriList.value = _uriList.value
    }
}