package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.team.parking.MainActivity
import com.team.parking.R
import com.team.parking.databinding.FragmentLoginBinding
import com.team.parking.databinding.FragmentProfileBinding
import com.team.parking.presentation.viewmodel.UserViewModel

private const val TAG = "ProfileFragment종건"

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding = FragmentProfileBinding.bind(view)
        userViewModel = (activity as MainActivity).userViewModel
        binding.apply {
            handlers = this@ProfileFragment
            lifecycleOwner = this@ProfileFragment
            viewModel = userViewModel
        }

        userViewModel.getProfile()

        binding.imageBackProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textMainCarChangeProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_myCarFragment)
        }
        Log.d(TAG, "onViewCreated: ${userViewModel.userLiveData.value?.profile_img}")

        Glide.with(this@ProfileFragment)
            .load(userViewModel.userLiveData.value?.profile_img)
            .error(R.drawable.ic_baseline_person_24)
            .into(binding.imageProfilePoint)

        binding.pencil.setOnClickListener() {
            it.visibility = View.INVISIBLE
            binding.check.visibility = View.VISIBLE
            binding.cancel.visibility = View.VISIBLE
        }

        binding.check.setOnClickListener(){

            userViewModel.updateProfileImage()

            it.visibility = View.INVISIBLE
            binding.cancel.visibility = View.INVISIBLE
            binding.pencil.visibility = View.VISIBLE
        }
        binding.cancel.setOnClickListener(){
            it.visibility = View.INVISIBLE
            binding.check.visibility = View.INVISIBLE
            binding.pencil.visibility = View.VISIBLE
        }

        binding.imageProfilePoint.setOnClickListener(){
            if(binding.check.visibility == View.VISIBLE){

            }
        }
    }
}