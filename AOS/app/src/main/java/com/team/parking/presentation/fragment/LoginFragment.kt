package com.team.parking.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.KakaoSdk.type
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.team.parking.BuildConfig.KAKAO_CLIENT_KEY
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.data.api.UserService
import com.team.parking.data.model.user.LoginRequest
import com.team.parking.databinding.FragmentLoginBinding
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "LoginFragment종건"

class LoginFragment : Fragment() {

    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var mainActivity : MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLoginBinding = FragmentLoginBinding.bind(view)
        fragmentLoginBinding.apply {
            handlers = this@LoginFragment
            lifecycleOwner = this@LoginFragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    fun setOnLogin(){
        KakaoSdk.init(mainActivity, "${KAKAO_CLIENT_KEY}")
        // 이메일 로그인 콜백
        val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {

                Log.e(TAG, "로그인 실패:콜백 $error")
            } else if (token != null) {
                Log.e(TAG, "로그인 성공:콜백 ${token.accessToken}")
            }
        }

       // 카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(mainActivity)) {
            // 카카오톡 로그인
            UserApiClient.instance.loginWithKakaoTalk(mainActivity) { token, error ->
                // 로그인 실패 부분
                if (error != null) {
                    Log.e(TAG, "로그인 실패 $error")
                    // 사용자가 취소
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                        return@loginWithKakaoTalk
                    }
                    // 다른 오류
                    else {
                        UserApiClient.instance.loginWithKakaoAccount(mainActivity, callback = mCallback) // 카카오 이메일 로그인
                    }
                }
                // 로그인 성공 부분
                else if (token != null) {
                    Log.e(TAG, "로그인 성공 ${token.accessToken}")

                    CoroutineScope(Dispatchers.IO).launch {
                        val response = App.userRetrofit.create(UserService::class.java).login(
                            LoginRequest(
                            "kakao", token.accessToken)
                        )

                        if(response.isSuccessful){
                            Log.d(TAG, "success")
                        } else {
                            Log.d(TAG, "login: ${response.code()}")
                        }
                    }

                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(mainActivity, callback = mCallback) // 카카오 이메일 로그인
        }
    }


    fun setOnSignUp(){
        findNavController().navigate(R.id.action_login_fragment_to_signUpFragment)
    }
}