package com.iobytex.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iobytex.domain.Task
import com.iobytex.domain.TaskWithTypes
import com.iobytex.task.databinding.RowChipTypeBinding
import com.iobytex.task.databinding.TaskDoneCardLayoutBinding
import com.iobytex.task.databinding.TodayUpcomingTaskCardLayoutBinding
import com.iobytex.utils.Utils


private  const val ITEM_VIEW_TYPE_TODAY_AND_UPCOMING = 0
private const val  ITEM_VIEW_TYPE_TASK_DONE = 1

class TasksAdapter(private val clickListener: TaskItemClickListener) : ListAdapter<TaskWithTypes,RecyclerView.ViewHolder>(TasksItemCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return when(viewType){
            ITEM_VIEW_TYPE_TODAY_AND_UPCOMING -> TodayAndUpcComingTasksItemViewHolder.from(parent)
            ITEM_VIEW_TYPE_TASK_DONE -> TaskDoneItemViewHolder.from(parent)
           else -> throw ClassCastException("Unknown viewType ${viewType.toString()}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TodayAndUpcComingTasksItemViewHolder ->{
                holder.bind(getItem(position),clickListener)
            }
            is TaskDoneItemViewHolder ->{
                holder.bind(getItem(position),clickListener)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).task.completed){
                true -> ITEM_VIEW_TYPE_TASK_DONE
                false -> ITEM_VIEW_TYPE_TODAY_AND_UPCOMING
        }
    }

    class TodayAndUpcComingTasksItemViewHolder private  constructor(
        private val binding: TodayUpcomingTaskCardLayoutBinding,
        private val chipTypeBinding: RowChipTypeBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(taskWithTypes: TaskWithTypes, clickListener: TaskItemClickListener){
            binding.taskWithTypes = taskWithTypes
            for (value in taskWithTypes.type){
                chipTypeBinding.chipType.text = value.title
                binding.chipGroup.addView(chipTypeBinding.chipType)
            }
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : TodayAndUpcComingTasksItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TodayUpcomingTaskCardLayoutBinding.inflate(layoutInflater,parent,false)
                val chipTypeBinding = RowChipTypeBinding.inflate(layoutInflater,parent,false)
                return TodayAndUpcComingTasksItemViewHolder(binding,chipTypeBinding)
            }
        }
    }

    class TaskDoneItemViewHolder private constructor(
        private val binding: TaskDoneCardLayoutBinding,
        private val chipTypeBinding: RowChipTypeBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(taskWithTypes: TaskWithTypes, clickListener: TaskItemClickListener){
            binding.taskWithTypes = taskWithTypes
            for (value in taskWithTypes.type){
                chipTypeBinding.chipType.text = value.title
                binding.chipGroup.addView(chipTypeBinding.chipType)
            }
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : TaskDoneItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskDoneCardLayoutBinding.inflate(layoutInflater,parent,false)
                val chipTypeBinding = RowChipTypeBinding.inflate(layoutInflater,parent,false)
                return TaskDoneItemViewHolder(binding,chipTypeBinding)
            }
        }
    }

}


object TasksItemCallback : DiffUtil.ItemCallback<TaskWithTypes>(){
    override fun areItemsTheSame(oldItem: TaskWithTypes, newItem: TaskWithTypes): Boolean = oldItem.task.taskId == newItem.task.taskId

    override fun areContentsTheSame(oldItem: TaskWithTypes, newItem: TaskWithTypes): Boolean = oldItem == newItem

}



class TaskItemClickListener(val clickListener: (view:View,taskWithTypes: TaskWithTypes,type: Utils.TaskFlow)-> Unit){
    fun  onClick(view: View,taskWithTypes: TaskWithTypes,type: Utils.TaskFlow) = clickListener(view,taskWithTypes,type)
}
