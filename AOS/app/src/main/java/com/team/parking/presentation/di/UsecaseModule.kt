package com.team.parking.presentation.di

import com.team.parking.domain.repository.MapRepository
import com.team.parking.domain.repository.SearchRepository
import com.team.parking.domain.usecase.GetMapDataUseCase
import com.team.parking.domain.usecase.GetSearchAddressUseCase
import com.team.parking.domain.usecase.GetSearchDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class UsecaseModule {

    @Singleton
    @Provides
    fun provideGetMapDataUseCase(
        mapRepository: MapRepository
    ) : GetMapDataUseCase{
        return GetMapDataUseCase(mapRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchDataUsecase(
        searchRepository: SearchRepository
    ) : GetSearchDataUseCase{
        return GetSearchDataUseCase(searchRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchAddressUsecase(
        searchRepository: SearchRepository
    ) : GetSearchAddressUseCase{
        return GetSearchAddressUseCase(searchRepository)
    }

}