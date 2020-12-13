package com.quyT.sodokuresovle

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.quyT.sodokuresovle.databinding.ItemBinding


class Adapter(val listItem: ArrayList<Int>) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

}

class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Int) {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val param = binding.rlRoot.layoutParams
        param.width = screenWidth/9
        param.height = screenWidth/9
        binding.etNum.setText(if (item == 0) "" else item.toString())
        if (adapterPosition == 2 || adapterPosition == 5){
            binding.vRight.background = ContextCompat.getDrawable(binding.root.context,R.color.black)
            val paramm = binding.vRight.layoutParams
            paramm.width = 3
            binding.vRight.layoutParams = paramm
        }
        binding.etNum.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                binding.rlRoot.setBackgroundColor(ContextCompat.getColor(binding.root.context,R.color.red))
            }else{
                binding.rlRoot.setBackgroundColor(ContextCompat.getColor(binding.root.context,R.color.white))
            }
        }

    }
}