package com.team.parking.presentation.utils

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import com.team.parking.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class App : Application(){

    companion object {
        lateinit var userRetrofit: Retrofit
    }
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_KEY)

        KakaoSdk.init(this, "${BuildConfig.KAKAO_CLIENT_KEY}")

        userRetrofit= Retrofit.Builder()
            .baseUrl("http://k8d108.p.ssafy.io:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
