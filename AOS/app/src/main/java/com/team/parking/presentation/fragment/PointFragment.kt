package com.team.parking.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.parking.BuildConfig
import com.team.parking.R
import com.team.parking.databinding.FragmentPointBinding


class PointFragment : Fragment() {
   private lateinit var fragmentPointBinding: FragmentPointBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentPointBinding = FragmentPointBinding.bind(view)
        fragmentPointBinding.apply {
            select1000Point.setOnClickListener {
                clearCheck()
                select1000Point.isChecked = true
                textPricePoint.text = "1000 원"
            }
            select3000Point.setOnClickListener {
                clearCheck()
                select3000Point.isChecked = true
                textPricePoint.text = "3000 원"
            }
            select5000Point.setOnClickListener {
                clearCheck()
                select5000Point.isChecked = true
                textPricePoint.text = "5000 원"
            }
            select10000Point.setOnClickListener {
                clearCheck()
                select10000Point.isChecked = true
                textPricePoint.text = "10000 원"
            }
            imageBackPoint.setOnClickListener {
                requireActivity().onBackPressed()
            }
            buttonCancelPoint.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    fun clearCheck(){
        fragmentPointBinding.apply {
            select1000Point.isChecked = false
            select3000Point.isChecked = false
            select5000Point.isChecked = false
            select10000Point.isChecked = false
        }
    }
}