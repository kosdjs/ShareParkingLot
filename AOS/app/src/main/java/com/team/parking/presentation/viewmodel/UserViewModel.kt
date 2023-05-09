package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk.type
import com.navercorp.nid.oauth.NidOAuthPreferencesManager.accessToken
import com.team.parking.R
import com.team.parking.data.api.UserAPIService
import com.team.parking.data.model.user.LoginResponse
import com.team.parking.data.model.user.User
import com.team.parking.data.util.Resource
import com.team.parking.domain.usecase.*
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val TAG = "userviewmodel종건"

class UserViewModel(
    private val app: Application,
    private val getUserUseCase: GetUserUseCase,
    private val postUserUseCase: PostUserUseCase,
    private val putFcmTokenUseCase: PutFcmTokenUseCase,
    private val postAuthMessageUseCase: PostAuthMessageUseCase,
    private val getAuthMessageUseCase: PostAuthMessageUseCase,
    private val getEmailUseCase: GetEmailUseCase,
) : AndroidViewModel(app) {
    val application = App()


    var _userName: String = ""
    var _type: String = ""
    var _profileImage: String = ""
    var _phone: String = ""
    var _password: MutableLiveData<String> = MutableLiveData()
    var _email: MutableLiveData<String> = MutableLiveData()
    var _check_email: Boolean = false
    var _check_phone: Boolean = false
    var _check_password: Boolean = false
    var _login_email: String = ""
    var _login_password: String = ""
    var _social_id: String = ""
    var validTime: MutableLiveData<String> = MutableLiveData()
    val timeLiveData: LiveData<String> get() = validTime
    var `try`: Int = 0

    private var _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse : LiveData<Resource<LoginResponse>> get() = _loginResponse
    // 여기까지 login 전
    private var _userLiveData: MutableLiveData<Resource<User>> = MutableLiveData()
    val userLiveData: LiveData<Resource<User>> get() = _userLiveData
    private var _fcm_token: MutableLiveData<String> = MutableLiveData()
    val fcm_token: LiveData<String> get() = _fcm_token


    fun setOnJumoLogin() = viewModelScope.launch(Dispatchers.IO) {

        getFCMToken { fcm_token ->
            Log.d(TAG, "setOnJumoLogin: ${_login_password}")

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = getUserUseCase.execute(
                        type = "jumo",
                        null,
                        email = _login_email,
                        password = _login_password,
                        fcm_token = fcm_token,
                        name = null,
                        profile_img = null,
                        social_id = null
                    )

                    _loginResponse.postValue(result)
                }catch(e:Exception){
                    _loginResponse.postValue(Resource.Error(e.message.toString()))
                }
            }
        }
    }

    fun setOnKakaoLogin(social_id:String, accessToken : String) = viewModelScope.launch(Dispatchers.IO) {
        getFCMToken { fcm_token ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = getUserUseCase.execute(
                        type = "kakao",
                        accessToken = accessToken,
                        email = null,
                        password = null,
                        fcm_token = fcm_token,
                        name = null,
                        profile_img = null,
                        social_id = social_id
                    )

                    _loginResponse.postValue(result)
                }catch(e:Exception){
                    _loginResponse.postValue(Resource.Error(e.message.toString()))
                }
            }
        }
    }

    fun setOnNaverLogin(social_id : String?,user_name : String?,user_image : String?) = viewModelScope.launch(Dispatchers.IO) {
        getFCMToken { fcm_token ->
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = getUserUseCase.execute(
                        type = "naver",
                        accessToken = null,
                        email = null,
                        password = null,
                        fcm_token = fcm_token,
                        name = user_name,
                        social_id = social_id,
                        profile_img = user_image,
                    )

                    _loginResponse.postValue(result)
                }catch(e:Exception){
                    _loginResponse.postValue(Resource.Error(e.message.toString()))
                }
            }
        }
    }


    fun updateFcmToken(token: String) {
        _fcm_token.postValue(token)
    }

    fun signReset() {
        _userName = ""
        _phone = ""
        _email = MutableLiveData()
        _type = ""
        _profileImage = ""
        _password = MutableLiveData()
        _social_id = ""

        _check_email = false
        _check_phone = false
        _check_password = false
    }


    private fun getFCMToken(callback: (String?) -> Unit) {
        var token: String? = null

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                callback(null)
            }

            // Get new FCM registration token
            token = task.result

            // Log and toast
            Log.d(TAG, "FCM Token is ${token}")
            callback(token)
        })

    }

    fun login(
        loginResponse: Resource<LoginResponse>
    ) {
        val user :User = User(
            loginResponse.data?.user_id!!,
            loginResponse.data?.phone!!,
            loginResponse.data?.email!!,
            loginResponse.data?.name!!,
            loginResponse.data?.profile_img,
            loginResponse.data.ptHas,
            loginResponse.data.type!!,
            loginResponse.data.social_id!!,
            loginResponse.data.fcm_token
            )
        val user1 : Resource.Success<User> = Resource.Success(user)
        _userLiveData.postValue(user1)
    }
}