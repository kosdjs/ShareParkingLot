package com.team.parking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.kakao.sdk.common.util.Utility
import com.team.parking.data.api.UserService
import com.team.parking.data.model.user.User
import com.team.parking.databinding.ActivityMainBinding
import com.team.parking.presentation.utils.App

import com.team.parking.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity_지훈"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var userViewModel: UserViewModel
    lateinit var navigationDrawer : DrawerLayout
    lateinit var navigationView : NavigationView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationController()
        setNavigationDrawerInit()
        setOnClickNavigationDrawerItem()

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

    }
    
    fun setOnClickNavigationDrawerItem(){
        binding.navigationFragmentMap.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_point -> {
                    Log.i(TAG, "onNavigationItemSelected:1")
                }
                else -> {
                    Log.i(TAG, "onNavigationItemSelected:2")
                }
            }
            true
        }
    }


    /**
     * MapFragment에서 사용할 drawer 초기화
     */
    private fun setNavigationDrawerInit(){
        navigationDrawer = binding.drawer
        navigationView = binding.navigationFragmentMap
    }

    /**
     * Navigation Controller 등록
     */
    private fun setNavigationController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
    }

}