package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.team.parking.MainActivity
import com.team.parking.databinding.FragmentParkingLotDetailBinding
import com.team.parking.presentation.viewmodel.MapViewModel

private const val TAG = "ParkingLotDetailFragment_지훈"
class ParkingLotDetailFragment : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentParkingLotDetailBinding
    private lateinit var mapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentParkingLotDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewModel = (activity as MainActivity).mapViewModel
        binding.apply {
            vm = mapViewModel
            lifecycleOwner = this@ParkingLotDetailFragment
        }
        Log.i(TAG, "onViewCreated: ")
    }

}