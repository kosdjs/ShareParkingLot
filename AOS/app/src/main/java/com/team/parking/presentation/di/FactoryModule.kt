package com.team.parking.presentation.di

import android.app.Application
import com.team.parking.domain.usecase.*
import com.team.parking.presentation.viewmodel.MapViewModelFactory
import com.team.parking.presentation.viewmodel.SearchViewModelFactory
import com.team.parking.presentation.viewmodel.UserViewModel
import com.team.parking.presentation.viewmodel.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideMapViewModelFactory(
        app:Application,getMapDataUseCase: GetMapDataUseCase,getMapDetailDataUseCase: GetMapDetailDataUseCase
    ):MapViewModelFactory{
        return MapViewModelFactory(app,getMapDataUseCase,getMapDetailDataUseCase)
    }


    @Singleton
    @Provides
    fun provideSearchViewModelFactory(
        app:Application,searchDataUseCase: GetSearchDataUseCase
    ) : SearchViewModelFactory{
        return SearchViewModelFactory(app,searchDataUseCase)
    }

    @Singleton
    @Provides
    fun provideUserViewModelFactory(
        app:Application,
        getUserUseCase: GetUserUseCase,
        postUserUseCase: PostUserUseCase,
        putFcmTokenUseCase : PutFcmTokenUseCase,
        postAuthMessageUseCase: PostAuthMessageUseCase,
        getAuthMessageUseCase: PostAuthMessageUseCase,
        getEmailUseCase: GetEmailUseCase,
        ) : UserViewModelFactory {
        return UserViewModelFactory(app,getUserUseCase)
    }
}