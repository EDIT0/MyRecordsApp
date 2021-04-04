package com.privatememo.j.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.privatememo.j.datamodel.CategoryInfo
import com.privatememo.j.databinding.CategoryadapterBinding
import com.privatememo.j.datamodel.CategoryInfo2

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var items = ArrayList<CategoryInfo2>()

    class ViewHolder(binding : CategoryadapterBinding): RecyclerView.ViewHolder(binding.root){
        var binding = binding

        fun bind(info : CategoryInfo2){
            binding.categoryInfo2Model = info

            binding.root.setOnClickListener {
                /*var intent = Intent(binding.root.context, SecondActivity::class.java)
                intent.putExtra("title",movies.title)
                intent.putExtra("date",movies.release_date)
                intent.putExtra("rating",movies.vote_average)
                intent.putExtra("overview",movies.overview)
                intent.putExtra("poster",movies.poster_path)
                binding.root.context.startActivity(intent)*/
            }
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