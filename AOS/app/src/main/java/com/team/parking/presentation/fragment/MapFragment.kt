package com.team.parking.presentation.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentMapBinding

private const val TAG = "MapFragment_지훈"
class MapFragment : Fragment() , OnMapReadyCallback {
    private lateinit var fragmentMapBinding: FragmentMapBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMapBinding = DataBindingUtil.bind<FragmentMapBinding>(view)!!
        init()
    }

    /**
     * 초기화 함수 모음
     */
    private fun init() {
        setDatabinding()
        setOnClickNavigationDrawerItem()
        initMap()
    }


    /**
     * NaverMap Option
     */
    private fun initMap() {

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.fragment_fragment_map_maps) as com.naver.maps.map.MapFragment?
            ?:com.naver.maps.map.MapFragment.newInstance().also{
                fm.beginTransaction().add(R.id.fragment_fragment_map_maps,it).commit()
            }
        mapFragment.getMapAsync(this)
    }


    /**
     * 네이게이션 뷰 클릭리스너
     */
    fun setOnClickNavigationDrawerItem() {
        val activity = activity as MainActivity
        activity.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_point -> {
                    findNavController().navigate(R.id.action_map_fragment_to_pointFragment)
                }
                R.id.item_trasaction -> {
                    findNavController().navigate(R.id.action_map_fragment_to_transactionHistoryFragment)
                }
                else -> {
                    findNavController().navigate(R.id.action_map_fragment_to_favoriteFragment)
                }
            }
            activity.navigationDrawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    /**
     * databinding 초기화
     */
    private fun setDatabinding() {
        fragmentMapBinding.apply {
            handlers = this@MapFragment
            lifecycleOwner = this@MapFragment
        }
    }
    /**
     * 햄버거 클릭시 drawer 생성
     */
    fun onNavigationDrawer() {
        (activity as MainActivity).navigationDrawer.openDrawer(GravityCompat.START)
    }

    /**
     * 내위치 버튼 추가 및 최소 줌 최대 줌 추가
     */
    private fun mapSetting(){
        naverMap.uiSettings.isLocationButtonEnabled = true
        naverMap.minZoom = 8.0
        naverMap.maxZoom = 18.0
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        mapSetting()
        // NaverMap의 locationSource 설정
        naverMap.locationSource = locationSource
        Log.i(TAG, "onMapReady: ")
        // 위치 권한 허용 확인
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                PERMISSIONS,
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                Toast.makeText(context, "권한이 거부되어 현위치를 표시할 수 없습니다.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}


