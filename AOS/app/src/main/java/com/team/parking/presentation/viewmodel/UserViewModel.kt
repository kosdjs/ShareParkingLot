package com.team.parking.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.parking.data.model.user.User

private val TAG = "userviewmodel종건"

class UserViewModel : ViewModel() {
    var _userName: String = ""
    var _type: String = ""
    var _profileImage: String = ""
    var _phone: String = ""
    var _password: String = ""
    var _email: MutableLiveData<String> = MutableLiveData()
    var _check_email: Boolean = false
    var _check_phone: Boolean = false
    var _check_password: Boolean = false
    var _login_email :String = ""
    var _login_password :String = ""
    // 여기까지 login 전
    var user: User? = null

    fun login(
        _login_user_id: Long,
        _login_user_name: String,
        _login_user_type: String,

        _login_user_phone: String,
        _login_user_email: String,
        _login_user_profile_img: String,
        _logun_user_ptHas: Int
    ) {
        user = User(
            _login_user_id,
            _login_user_phone,
            _login_user_email,
            _login_user_name,
            _login_user_profile_img,
            _logun_user_ptHas, _login_user_type
        )
    }


    fun signReset() {
        _userName = ""
        _phone = ""
        _email = MutableLiveData()
        _type = ""
        _profileImage = ""
        _password = ""
        _check_email = false
        _check_phone = false
        _check_password = false
    }
}