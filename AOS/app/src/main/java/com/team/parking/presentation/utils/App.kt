package com.team.parking.presentation.utils

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import com.team.parking.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class App : Application(){
    companion object{
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_CLIENT_KEY)

        retrofit = Retrofit.Builder()
            .baseUrl("http://k8d108.p.ssafy.io:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}
