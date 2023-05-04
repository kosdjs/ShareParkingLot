package com.team.parking.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team.parking.data.model.parkinglot.ShareLotResponse
import com.team.parking.databinding.ItemMyShareParkingLotBinding

class ShareParkingLotListAdapter : RecyclerView.Adapter<ShareParkingLotListAdapter.ListViewHolder>(){

    private lateinit var onShareParkingLotItemClickListener: ShareParkingLotItemClickListener

    private val callback = object : DiffUtil.ItemCallback<ShareLotResponse>(){
        override fun areItemsTheSame(oldItem: ShareLotResponse, newItem: ShareLotResponse): Boolean {
            return oldItem.shareLotId == newItem.shareLotId
        }

        override fun areContentsTheSame(oldItem: ShareLotResponse, newItem: ShareLotResponse): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,callback)

    inner class ListViewHolder(val binding : ItemMyShareParkingLotBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : ShareLotResponse){
            binding.textNameItemMyShareParkingLot.text = data.shaName
            binding.imageDeleteItemMyShareParkingLot.setOnClickListener {
                onShareParkingLotItemClickListener.onClick(it, layoutPosition, data.shareLotId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMyShareParkingLotBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setShareParkingLotItemClickListener(onItemClickListener: ShareParkingLotItemClickListener){
        onShareParkingLotItemClickListener = onItemClickListener
    }

    interface ShareParkingLotItemClickListener{
        fun onClick(view: View,position:Int,data:Long)
    }
}