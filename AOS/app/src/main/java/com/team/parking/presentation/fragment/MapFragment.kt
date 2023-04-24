package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentMapBinding

private const val TAG = "MapFragment_지훈"
class MapFragment : Fragment() , OnMapReadyCallback{
    private lateinit var fragmentMapBinding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMapBinding = DataBindingUtil.bind<FragmentMapBinding>(view)!!
        init()
    }

    /**
     * 초기화 함수 모음
     */
    private fun init(){
        setDatabinding()
        setOnClickNavigationDrawerItem()
        initMap()
    }

    /**
     * NaverMap Option
     */
    private fun initMap(){
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
    fun setOnClickNavigationDrawerItem(){
        val activity = activity as MainActivity
        activity.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_point -> {
                    findNavController().navigate(R.id.action_map_fragment_to_pointFragment)
                }
                R.id.item_trasaction->{
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
    private fun setDatabinding(){
        fragmentMapBinding.apply {
            handlers = this@MapFragment
            lifecycleOwner = this@MapFragment
        }
    }


    /**
     * 햄버거 클릭시 drawer 생성
     */
    fun onNavigationDrawer(){
        (activity as MainActivity).navigationDrawer.openDrawer(GravityCompat.START)
    }


    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        Log.i(TAG, "onMapReady: ${naverMap.cameraPosition}")

        naverMap.addOnCameraChangeListener{ reason , animated ->
            Log.i(TAG, "onMapReady: ${naverMap.cameraPosition}")
        }
    }


}