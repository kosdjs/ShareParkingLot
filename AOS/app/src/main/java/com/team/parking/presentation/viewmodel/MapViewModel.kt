package com.team.parking.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.parking.data.model.map.MapRequest
import com.team.parking.data.model.map.ParkingLot
import com.team.parking.domain.usecase.GetMapDataUseCase
import com.team.parking.presentation.utils.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MapViewModel_지훈"
class MapViewModel(
    private val app:Application,
    val getMapDataUseCase: GetMapDataUseCase
) : AndroidViewModel(app){
    //지도 주차장 데이터
    val mapDatas : MutableLiveData<List<ParkingLot>> = MutableLiveData()


    /**
     * 서버로부터 주차장 데이터 받기
     */
    fun getMapDatas(mapRequest: MapRequest) = viewModelScope.launch(Dispatchers.IO) {
        val apiResult = getMapDataUseCase.execute(mapRequest)
        if(apiResult!=null){
            mapDatas.postValue(apiResult.body())
        }
        else{
            Log.e(TAG, "서버로부터 지도 데이터를 불러오는데 실패했습니다")
        }
    }


}