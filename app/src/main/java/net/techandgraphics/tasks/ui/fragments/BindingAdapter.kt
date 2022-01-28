package net.techandgraphics.tasks.ui.fragments

import android.graphics.Paint
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import net.techandgraphics.tasks.R
import net.techandgraphics.tasks.ext.Ext
import net.techandgraphics.tasks.model.Task
import net.techandgraphics.tasks.vo.TimeDate


@BindingAdapter("setLocation")
fun setLocation(view: AppCompatTextView, location: String?) {
    view.isVisible = location.isNullOrEmpty().not()
    location?.let { view.text = it }
}


@BindingAdapter("setPriority")
fun setPriority(view: AppCompatTextView, priority: Boolean) {
    view.isVisible = priority
}


@BindingAdapter("setDate")
fun setDate(view: AppCompatTextView, timestamp: Long) {
    view.text = TimeDate.getDate(timestamp)
}

@BindingAdapter("setTime")
fun setTime(view: AppCompatTextView, timestamp: Long) {
    view.text = TimeDate.getTime(timestamp)
}


@BindingAdapter("setBackground")
fun setBackground(view: View, @ColorRes color: Int) {
    view.setBackgroundColor(ContextCompat.getColor(view.context, color))
}

@BindingAdapter("setReminderResource")
fun setReminderResource(view: AppCompatImageView, notify: Boolean) {
    view.setImageResource(if (notify) R.drawable.ic_notifications_on else R.drawable.ic_notifications_off)
}


@BindingAdapter("setReminderCompoundDrawables")
fun setReminderCompoundDrawables(view: AppCompatTextView, notify: Boolean) {
    Ext.reminderCompoundDrawables(view, notify)
}


@BindingAdapter("setCompleteVisibility")
fun setCompleteVisibility(view: View, complete: Boolean) {
    view.isVisible = complete
}

@BindingAdapter("buttonText")
fun buttonText(view: AppCompatButton, task: Task) {
    view.text = if (task.id == 0) "Create task" else "Edit task"
}


@BindingAdapter("headingText")
fun headingText(view: AppCompatTextView, task: Task) {
    view.text = if (task.id > 0) task.title else "Create task"
}

@BindingAdapter("completeTask")
fun completeTask(view: AppCompatTextView, complete: Boolean) {
    view.paintFlags = if(complete) Paint.STRIKE_THRU_TEXT_FLAG else Paint.ANTI_ALIAS_FLAG
}