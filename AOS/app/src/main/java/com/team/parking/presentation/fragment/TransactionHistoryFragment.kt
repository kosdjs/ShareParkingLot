package com.team.parking.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.team.parking.R
import com.team.parking.databinding.FragmentTransactionHistoryBinding
import com.team.parking.presentation.viewmodel.TransactionHistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "TransactionHistoryFragm"

class TransactionHistoryFragment : Fragment() {
    private lateinit var fragmentTransactionHistoryBinding: FragmentTransactionHistoryBinding
    private lateinit var transactionHistoryViewModel: TransactionHistoryViewModel
    val calendar = Calendar.getInstance()

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
        transactionHistoryViewModel = ViewModelProvider(requireActivity())[TransactionHistoryViewModel::class.java]
        transactionHistoryViewModel.month.observe(viewLifecycleOwner){
            calendar.set(Calendar.MONTH, it-1)
            setMonthText()
        }
        transactionHistoryViewModel.year.observe(viewLifecycleOwner){
            calendar.set(Calendar.YEAR, it)
            setMonthText()
        }
        transactionHistoryViewModel.setMonth(calendar.get(Calendar.MONTH) + 1)
        transactionHistoryViewModel.setYear(calendar.get(Calendar.YEAR))
        fragmentTransactionHistoryBinding.apply {
            imageBackTransactionHistory.setOnClickListener {
                requireActivity().onBackPressed()
            }
            layoutPurchaseTransactionHistory.setOnClickListener {
                textTitleSellTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                underlineSellTransactionHistory.visibility = View.GONE
                textTitlePurchaseTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color2))
                underlinePurchaseTransactionHistory.visibility = View.VISIBLE
                textTotalTransactionHistory.text = "누적 구매 금액"
                textTotalPriceTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color2))
                //recyclerview
            }
            layoutSellTransactionHistory.setOnClickListener {
                textTitlePurchaseTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                underlinePurchaseTransactionHistory.visibility = View.GONE
                textTitleSellTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color))
                underlineSellTransactionHistory.visibility = View.VISIBLE
                textTotalTransactionHistory.text = "누적 판매 금액"
                textTotalPriceTransactionHistory.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color))
                //recyclerview
            }
            imageLeftMonthTransactionHistory.setOnClickListener {
                transactionHistoryViewModel.setPreviousMonth()
                Log.d(TAG, "onViewCreated: left")
            }
            imageRightMonthTransactionHistory.setOnClickListener {
                transactionHistoryViewModel.setNextMonth()
                Log.d(TAG, "onViewCreated: right ${transactionHistoryViewModel.month.value}")
            }
        }
    }

    fun setMonthText(){
        fragmentTransactionHistoryBinding.apply {
            val formatter = SimpleDateFormat("yyyy.MM")
            textMonthTransactionHistory.text = formatter.format(calendar.time)
        }
    }
}