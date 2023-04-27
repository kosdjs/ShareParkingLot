package com.team.parking.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private val TAG = "userviewmodel종건"
class UserViewModel : ViewModel(){
    var _userName : String? = null
    var _type : String? = null
    var _profileImage : String? = null
    var _phone : String? = null

    var email : MutableLiveData<String> = MutableLiveData()


}