package net.techandgraphics.tasks.ui.fragments.task

import androidx.recyclerview.widget.DiffUtil
import net.techandgraphics.tasks.model.Category

object ListDiffUtil {
    val DiffUtil = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem.hashCode() == newItem.hashCode()
    }
}