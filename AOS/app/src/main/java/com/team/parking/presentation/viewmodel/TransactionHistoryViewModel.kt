package com.team.parking.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "TransactionHistoryViewM"

class TransactionHistoryViewModel : ViewModel() {
    private var _month = MutableLiveData<Int>()
    val month : LiveData<Int> get() = _month

    private var _year = MutableLiveData<Int>()
    val year : LiveData<Int> get() = _year

    fun setYear(year: Int){
        _year.value = year
    }

    fun setMonth(month: Int){
        _month.value = month
    }

    fun setPreviousMonth(){
        if(_month.value == 1){
            _year.value = _year.value?.minus(1)
            _month.value = 12
        } else {
            _month.value = _month.value?.minus(1)
        }
    }

    fun setNextMonth(){
        if(_month.value == 12){
            _year.value = _year.value?.plus(1)
            _month.value = 1
        } else {
            _month.value = _month.value?.plus(1)
        }
    }
}