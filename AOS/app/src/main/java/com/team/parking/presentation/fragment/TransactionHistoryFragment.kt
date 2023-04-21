package com.team.parking.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.team.parking.R
import com.team.parking.databinding.FragmentTransactionHistoryBinding


class TransactionHistoryFragment : Fragment() {
    private lateinit var fragmentTransactionHistoryBinding: FragmentTransactionHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTransactionHistoryBinding = FragmentTransactionHistoryBinding.bind(view)
    }

}