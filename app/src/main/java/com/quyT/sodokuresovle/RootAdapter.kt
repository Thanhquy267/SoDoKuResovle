package com.quyT.sodokuresovle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quyT.sodokuresovle.databinding.ItemRootBinding

class RootAdapter(var listRoot: ArrayList<ArrayList<Int>>) :
    RecyclerView.Adapter<RootViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder {
        return RootViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_root,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listRoot.size
    }

    override fun onBindViewHolder(holder: RootViewHolder, position: Int) {
        holder.bind(listRoot[position])
    }

    fun setData(listData: ArrayList<ArrayList<Int>>){
        val listClone = ArrayList<ArrayList<Int>>()
        ArrayList(listData).forEach {
            val list = ArrayList(it)
            listClone.add(list)
        }
        this.listRoot = ArrayList(listClone)
        notifyDataSetChanged()
    }
}

class RootViewHolder(val binding: ItemRootBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ArrayList<Int>) {
        val adapter = Adapter(item)
        binding.rvNum.adapter = adapter
        binding.rvNum.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
        if (adapterPosition == 2 || adapterPosition == 5){
            binding.vBot.background = ContextCompat.getDrawable(binding.root.context,R.color.black)
            val param = binding.vBot.layoutParams
            param.height = 3
            binding.vBot.layoutParams = param
        }
    }
}
