package com.privatememo.j.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.CategoryadapterBinding
import com.privatememo.j.datamodel.CategoryInfo2
import kotlinx.android.synthetic.main.categoryadapter.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var items = ArrayList<CategoryInfo2>()
    lateinit var itemClick: AdapterListener

    inner class ViewHolder(binding : CategoryadapterBinding): RecyclerView.ViewHolder(binding.root){
        var binding = binding

        init {
            itemView.setOnClickListener {
                itemClick.CategoryShortClick(this, itemView, adapterPosition)
            }
            itemView.cateimage.setOnClickListener {
                itemClick.CategoryImageClick(this, itemView, adapterPosition)
            }
            itemView.setOnLongClickListener {
                itemClick.CategoryLongClick(this, itemView, adapterPosition)
                return@setOnLongClickListener true
            }
        }

        fun bind(info : CategoryInfo2){
            binding.categoryInfo2Model = info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        var binding = CategoryadapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}