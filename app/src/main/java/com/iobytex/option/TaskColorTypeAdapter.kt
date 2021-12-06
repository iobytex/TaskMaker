package com.iobytex.option

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iobytex.task.databinding.FillTaskColorPickerBinding
import kotlin.random.Random
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.widget.ImageView
import androidx.palette.graphics.Palette
import com.iobytex.task.R
import timber.log.Timber


internal class TaskColorTypeAdapter(private val recyclerView: RecyclerView) : ListAdapter<Palette.Swatch,TaskColorTypeAdapter.TaskColorTypeItemViewHolder>(TaskColorTypeDiffCallback) {

    private var colorPosition: Int = Random(itemCount).nextInt(itemCount)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskColorTypeItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =  FillTaskColorPickerBinding.inflate(layoutInflater,parent,false)
        return TaskColorTypeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskColorTypeItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

     internal inner class TaskColorTypeItemViewHolder constructor(val binding: FillTaskColorPickerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(swatch: Palette.Swatch){
            if (colorPosition == adapterPosition){
                when (val background = binding.colorPick.background) {
                    is ShapeDrawable -> {
                        // cast to 'ShapeDrawable'
                        background.paint.color = swatch.rgb
                    }
                    is GradientDrawable -> {
                        // cast to 'GradientDrawable'
//                        val gradientDrawable = background as GradientDrawable
//                        gradientDrawable.c(ContextCompat.getColor(mContext, R.color.colorToSet))
                    }
                    is ColorDrawable -> {
                        // alpha value may need to be set again after this call
                        background.color = swatch.rgb
                    }
                }
                //setbackground choosen
                //initialize value
                //pass color data to clicklistener
            }else{
                binding.colorPick.imageAlpha = 0
                when (val background = binding.colorPick.background) {
                    is ShapeDrawable -> {
                        // cast to 'ShapeDrawable'
                        background.paint.color = swatch.rgb
                        binding.colorPick.setColorFilter(R.color.transparent)
                    }
                    is GradientDrawable -> {
                        // cast to 'GradientDrawable'
//                        val gradientDrawable = background as GradientDrawable
//                        gradientDrawable.c(ContextCompat.getColor(mContext, R.color.colorToSet))
                    }
                    is ColorDrawable -> {
                        // alpha value may need to be set again after this call
                        background.color = swatch.rgb
                        binding.colorPick.setColorFilter(R.color.transparent)
                    }
                }
            }
            binding.colorPick.setOnClickListener {
                    if (colorPosition == adapterPosition){
                        Timber.d("Selected already")
                    }else {
                        with(recyclerView.findViewHolderForAdapterPosition(adapterPosition)?.itemView as ImageView){
                            this.setColorFilter(R.color.transparent)
                        }
                        binding.colorPick.setColorFilter(R.color.white)
                        colorPosition = adapterPosition
                        //get the view selected and unselect it
                        //then select the new view and initialize value
                    }

            }

        }
    }
}

object TaskColorTypeDiffCallback : DiffUtil.ItemCallback<Palette.Swatch>(){
    override fun areItemsTheSame(oldItem: Palette.Swatch, newItem: Palette.Swatch): Boolean = oldItem.rgb == newItem.rgb
    override fun areContentsTheSame(oldItem: Palette.Swatch, newItem: Palette.Swatch): Boolean = oldItem == newItem
}