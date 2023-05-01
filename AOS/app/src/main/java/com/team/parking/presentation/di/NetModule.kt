package com.team.parking.presentation.di

import com.team.parking.data.api.MapAPIService
import com.team.parking.data.api.SearchAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MapRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SearchRetrofit

    @Singleton
    @Provides
    @MapRetrofit
    fun provideMapRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://k8d108.p.ssafy.io:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMapService(@MapRetrofit mapRetrofit:Retrofit):MapAPIService{
        return mapRetrofit.create(MapAPIService::class.java)
    }

    @Singleton
    @Provides
    @SearchRetrofit
    fun provideSearchRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchService(@SearchRetrofit searchRetrofit:Retrofit):SearchAPIService{
        return searchRetrofit.create(SearchAPIService::class.java)
    }




}