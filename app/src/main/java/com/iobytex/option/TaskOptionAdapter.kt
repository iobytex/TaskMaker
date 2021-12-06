package com.iobytex.option

import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iobytex.domain.OptionTask
import com.iobytex.task.databinding.TaskOptionItemLayoutBinding

class TaskOptionAdapter(private val clickListener: InputTaskListener) : ListAdapter<OptionTask,TaskOptionAdapter.InputTaskItemViewHolder>(InputTaskDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputTaskItemViewHolder {
        return InputTaskItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: InputTaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    class InputTaskItemViewHolder private  constructor(val binding: TaskOptionItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(optionTask: OptionTask, clickListener: InputTaskListener) {
            binding.optionTask = optionTask
//            binding.taskImage.setImageResource(inputTask.imageIcon)
//            binding.title.text = inputTask.title
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): InputTaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskOptionItemLayoutBinding.inflate(layoutInflater,parent,false)
                return InputTaskItemViewHolder(binding)
            }
        }
    }

}

object InputTaskDiffCallback : DiffUtil.ItemCallback<OptionTask>(){
    override fun areItemsTheSame(oldItem: OptionTask, newItem: OptionTask): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: OptionTask, newItem: OptionTask): Boolean = oldItem == newItem

}

class InputTaskListener(val clickListener: (view:View,optionId:Int)-> Unit) {
    fun onClick(view: View,optionId:Int) = clickListener(view,optionId)
}