package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.parking.domain.usecase.GetSearchDataUseCase
import com.team.parking.domain.usecase.GetUserUseCase

class UserViewModelFactory (
    private val app : Application,
    val getUserUseCase: GetUserUseCase
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(app,getUserUseCase) as T
        }
}