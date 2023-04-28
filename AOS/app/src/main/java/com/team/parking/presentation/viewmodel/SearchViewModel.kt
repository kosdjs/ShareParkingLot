package com.team.parking.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.team.parking.domain.usecase.GetSearchDataUseCase

class SearchViewModel(
    private val app : Application,
    val searchDataUseCase: GetSearchDataUseCase
    ) : AndroidViewModel(app){


}