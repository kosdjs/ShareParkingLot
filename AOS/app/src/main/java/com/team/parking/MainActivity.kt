package com.team.parking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.team.parking.databinding.ActivityMainBinding
import com.team.parking.databinding.SideHeaderBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity_지훈"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var headerBinding: SideHeaderBinding
    lateinit var navigationDrawer : DrawerLayout
    lateinit var navigationView : NavigationView
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        headerBinding = SideHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationController()
        setNavigationDrawerInit()
        setOnClickNavigationDrawerItem()
        setProfileFragmentNavigation()
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
        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    /**
     * Navigation Controller 등록
     */
    private fun setNavigationController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_activity_main) as NavHostFragment
        navController = navHostFragment.navController
    }

    /**
     * Profile Fragment Navigation
     */
    private fun setProfileFragmentNavigation(){
        binding.navigationFragmentMap.addHeaderView(headerBinding.root)
        headerBinding.layoutNicknameSideHeader.setOnClickListener {
            navController.navigate(R.id.action_map_fragment_to_profileFragment)
            binding.drawer.closeDrawers()
        }
        headerBinding.buttonSideHeader.setOnClickListener {
            navController.navigate(R.id.action_map_fragment_to_myShareParkingLotFragment)
            binding.drawer.closeDrawers()
        }
    }
}