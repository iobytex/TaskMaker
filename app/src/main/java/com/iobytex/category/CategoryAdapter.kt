package com.iobytex.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iobytex.domain.Category
import com.iobytex.task.databinding.CategoryItemLayoutBinding

class CategoryAdapter : ListAdapter<Category,CategoryAdapter.CategoryItemViewViewHolder>(CategoryItemDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewViewHolder {
        return CategoryItemViewViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryItemViewViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class CategoryItemViewViewHolder private  constructor(private val binding: CategoryItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Category?) {
            binding.category = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : CategoryItemViewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryItemLayoutBinding.inflate(layoutInflater,parent,false)
                return CategoryItemViewViewHolder(binding)
            }
        }
    }

}

object CategoryItemDiffCallback : DiffUtil.ItemCallback<Category>(){
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem == newItem

}

