package com.team.parking.presentation.di

import com.team.parking.domain.repository.MapRepository
import com.team.parking.domain.repository.SearchRepository
import com.team.parking.domain.repository.UserRepository
import com.team.parking.domain.usecase.*
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
    fun provideGetMapDetailDataUseCase(
        mapRepository: MapRepository
    ) : GetMapDetailDataUseCase{
        return GetMapDetailDataUseCase(mapRepository)
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
    fun provideGetUserUsecase(
        userRepository: UserRepository
    ) : GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun providePostUserUsecase(
        userRepository: UserRepository
    ) : PostUserUseCase {
        return PostUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun providePostAuthMessageUseCase(
        userRepository: UserRepository
    ) : PostAuthMessageUseCase {
        return PostAuthMessageUseCase(userRepository)
    }
    @Singleton
    @Provides
    fun providePutFcmTokenUsecase(
        userRepository: UserRepository
    ) : PutFcmTokenUseCase {
        return PutFcmTokenUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideGetEmailUsecase(
        userRepository: UserRepository
    ) : GetEmailUseCase {
        return GetEmailUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideGetAuthMessageUsecase(
        userRepository: UserRepository
    ) : GetAuthMessageUseCase {
        return GetAuthMessageUseCase(userRepository)
    }
    @Singleton
    @Provides
    fun providePutProfileImageUsecase(
        userRepository: UserRepository
    ) : PutProfileImageUseCase {
        return PutProfileImageUseCase(userRepository)
    }
    @Singleton
    @Provides
    fun provideGetUserInfoUsecase(
        userRepository: UserRepository
    ) : GetUserInfoUseCase {
        return GetUserInfoUseCase(userRepository)
    }
}