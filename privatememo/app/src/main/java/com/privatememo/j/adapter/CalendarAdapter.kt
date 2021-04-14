package com.privatememo.j

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.CalendaradapterBinding
import com.privatememo.j.datamodel.MemoInfo2

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    var items = ArrayList<MemoInfo2>()
    lateinit var itemClick: AdapterListener

    inner class ViewHolder(binding : CalendaradapterBinding): RecyclerView.ViewHolder(binding.root){
        var binding = binding

        init {
            itemView.setOnClickListener {
                itemClick.CalendarShortClick(this, itemView, adapterPosition)
            }
        }

        fun bind(info : MemoInfo2){
            binding.memoInfo2ViewModel = info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.ViewHolder {
        var binding = CalendaradapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CalendarAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}