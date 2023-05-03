package com.team.parking.presentation.di

import android.app.Application
import com.team.parking.domain.usecase.GetMapDataUseCase
import com.team.parking.domain.usecase.GetSearchAddressUseCase
import com.team.parking.domain.usecase.GetSearchDataUseCase
import com.team.parking.presentation.viewmodel.*
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
        app:Application,getMapDataUseCase: GetMapDataUseCase
    ):MapViewModelFactory{
        return MapViewModelFactory(app,getMapDataUseCase)
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
    fun provideSearchAddressViewModelFactory(
        app:Application,searchAddressUseCase: GetSearchAddressUseCase
    ) : SearchAddressViewModelFactory{
        return SearchAddressViewModelFactory(app,searchAddressUseCase)
    }

    @Singleton
    @Provides
    fun provideShareParkingLotViewModelFactory(
        app:Application
    ) : ShareParkingLotViewModelFactory{
        return ShareParkingLotViewModelFactory(app)
    }
}