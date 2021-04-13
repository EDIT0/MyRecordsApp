package com.privatememo.j.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.SearchadapterBinding
import com.privatememo.j.datamodel.SearchInfo2

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var items = ArrayList<SearchInfo2>()
    lateinit var itemClick: AdapterListener

    inner class ViewHolder(binding : SearchadapterBinding): RecyclerView.ViewHolder(binding.root){
        var binding = binding

        init {
            itemView.setOnClickListener {
                itemClick.SearchShortClick(this, itemView, adapterPosition)
            }
            itemView.setOnLongClickListener {
                itemClick.SearchLongClick(this, itemView, adapterPosition)
                return@setOnLongClickListener true
            }
        }

        fun bind(info : SearchInfo2){
            binding.searchInfo2ViewModel = info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = SearchadapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}