package com.team.parking.presentation.di

import com.team.parking.data.api.MapAPIService
import com.team.parking.data.api.SearchAPIService
import com.team.parking.data.api.UserAPIService
import com.team.parking.data.repository.dataSource.MapRemoteDataSource
import com.team.parking.data.repository.dataSource.SearchRemoteDataSource
import com.team.parking.data.repository.dataSource.UserRemoteDataSource
import com.team.parking.data.repository.localSource.dataSourceImpl.MapRemoteDataSourceImpl
import com.team.parking.data.repository.localSource.dataSourceImpl.SearchRemoteDataSourceImpl
import com.team.parking.data.repository.localSource.dataSourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideMapRemoteDataSource(
        mapAPIService: MapAPIService
    ):MapRemoteDataSource{
        return MapRemoteDataSourceImpl(mapAPIService)
    }

    @Singleton
    @Provides
    fun provideSearchRemoteDataSource(
        searchAPIService: SearchAPIService
    ) : SearchRemoteDataSource{
        return SearchRemoteDataSourceImpl(searchAPIService)
    }


    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        userAPIService: UserAPIService
    ) : UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userAPIService)
    }
}