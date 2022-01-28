package net.techandgraphics.tasks.ui.fragments.task

import androidx.recyclerview.widget.DiffUtil
import net.techandgraphics.tasks.model.Task
import net.techandgraphics.tasks.vo.Color

object ListDiffUtil {
    val DiffUtil = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem.hashCode() == newItem.hashCode()
    }
}