package net.techandgraphics.tasks.ui.fragments.list

import androidx.recyclerview.widget.DiffUtil
import net.techandgraphics.tasks.model.Task

object ListDiffUtil {
    val DiffUtil = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}