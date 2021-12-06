package com.iobytex.binding

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.iobytex.domain.Category

@BindingAdapter("imageIcon")
fun ImageView.imageIcon(imageIcon: Int?){
    imageIcon?.let {
        setImageResource(imageIcon)
    }
}

@BindingAdapter("backgroundColor")
fun MaterialCardView.backgroundColor(color: Int?){
    color?.let {
        setCardBackgroundColor(color)
    }
}

@BindingAdapter("categoryTitle")
fun TextView.categoryTitle(title: String?){
    title?.let {
        text = title
    }
}

@BindingAdapter("taskDoneTitle")
fun TextView.taskDoneTitle(title: String?){
    title?.let{
        text = title
    }
}

@BindingAdapter("todayAndUpcomingTitle")
fun TextView.todayAndUpcomingTitle(title: String?){
    title?.let{
        text = title
    }
}

@BindingAdapter("taskOptionImage")
fun ImageView.taskOptionImage(resId: Int?){
    resId?.let {
        setImageResource(resId)
    }

}


