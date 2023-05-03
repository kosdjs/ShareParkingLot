package com.team.parking.presentation.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentAddShareParkingLotBinding
import com.team.parking.presentation.adapter.ShareParkingLotImageAdapter
import com.team.parking.presentation.viewmodel.SearchAddressViewModel
import com.team.parking.presentation.viewmodel.SearchAddressViewModelFactory
import com.team.parking.presentation.viewmodel.ShareParkingLotViewModel
import com.team.parking.presentation.viewmodel.ShareParkingLotViewModelFactory
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

private const val TAG = "AddShareParkingLotFragm"

@AndroidEntryPoint
class AddShareParkingLotFragment : Fragment() {

    @Inject
    lateinit var searchAddressViewModelFactory: SearchAddressViewModelFactory
    private lateinit var binding: FragmentAddShareParkingLotBinding
    private lateinit var searchAddressViewModel: SearchAddressViewModel
    private lateinit var shareParkingLotViewModel: ShareParkingLotViewModel
    private lateinit var shareParkingLotImageAdapter: ShareParkingLotImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddShareParkingLotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchAddressViewModel = ViewModelProvider(this, searchAddressViewModelFactory)[SearchAddressViewModel::class.java]
        binding.buttonAddShareParkingLot.setOnClickListener {
            findNavController().navigate(R.id.action_addShareParkingLotFragment_to_daySelectFragment)
        }
        binding.textAddressAddShareParkingLot.setOnClickListener {
            findNavController().navigate(R.id.action_addShareParkingLotFragment_to_searchAddressFragment)
        }
        (activity as MainActivity).searchAddressViewModel = searchAddressViewModel
        searchAddressViewModel.searchedAddress.observe(viewLifecycleOwner){
            requireActivity().runOnUiThread {
                if(it != null){
                    binding.textAddressAddShareParkingLot.text = it.address.address_name
                } else {
                    binding.textAddressAddShareParkingLot.text = "눌러서 주소를 검색해주세요."
                }
            }
        }
        shareParkingLotViewModel = (activity as MainActivity).shareParkingLotViewModel
        shareParkingLotViewModel.clearList()
        shareParkingLotImageAdapter = ShareParkingLotImageAdapter()
        binding.recyclerViewAddPictureAddShareParkingLot.apply {
            adapter = shareParkingLotImageAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        shareParkingLotViewModel.uriList.observe(viewLifecycleOwner){
            shareParkingLotImageAdapter.differ.submitList(it.toList())
        }
        binding.imageAddPictureAddShareParkingLot.setOnClickListener {
            checkPermisson()
        }
        //viewmodel 만들기, 주소 검색 searchfragment 재사용하기
    }

    val result = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == Activity.RESULT_OK){
            val uri = it.data!!.data
            if (uri != null){
                shareParkingLotViewModel.addImage(uri)
            }
        }
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        result.launch(intent)
    }

    val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            openGallery()
        }
    }

    private fun checkPermisson(){
        permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}