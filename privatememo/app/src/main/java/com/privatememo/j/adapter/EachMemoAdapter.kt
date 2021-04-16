package com.privatememo.j.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.EachmemoadapterBinding
import com.privatememo.j.datamodel.MemoInfo2
import com.privatememo.j.utility.MemberSettingModule
import kotlinx.android.synthetic.main.eachmemoadapter.view.*

class EachMemoAdapter : RecyclerView.Adapter<EachMemoAdapter.ViewHolder>() {

    var textSizeModule = MemberSettingModule

    var items = ArrayList<MemoInfo2>()
    lateinit var itemClick: AdapterListener

    inner class ViewHolder(binding : EachmemoadapterBinding): RecyclerView.ViewHolder(binding.root){
        var binding = binding

        init {
            itemView.setOnClickListener {
                itemClick.EachMemoShortClick(this, itemView, adapterPosition)
            }
            itemView.setOnLongClickListener {
                itemClick.EachMemoLongClick(this, itemView, adapterPosition)
                return@setOnLongClickListener true
            }
            itemView.title.setTextSize(textSizeModule.TitleSize.toFloat())
            itemView.content.setTextSize(textSizeModule.ContentSize.toFloat())
        }

        fun bind(info : MemoInfo2){
            binding.memoInfo2Model = info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EachMemoAdapter.ViewHolder {
        var binding = EachmemoadapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: EachMemoAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}