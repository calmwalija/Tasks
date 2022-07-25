package net.techandgraphics.tasks.ui.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.techandgraphics.tasks.databinding.FragmentListItemBinding
import net.techandgraphics.tasks.model.Task
import net.techandgraphics.tasks.ui.fragments.list.ListFragmentAdapter.ViewHolder
import net.techandgraphics.tasks.ui.fragments.list.Listener.*
import net.techandgraphics.tasks.vo.Utils


enum class Listener {
    NOTIFICATION, TIME, DATE, CLICK
}

class ListFragmentAdapter(
    val listener: (Listener, Task) -> Unit
) : ListAdapter<Task, ViewHolder>(ListDiffUtil.DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: FragmentListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) = binding.apply {
            this.task = task
            this.category = Utils.category.filter { it.backgroundColor == task.color }[0]
            executePendingBindings()
        }

        init {
            binding.apply {
                timeTv.setOnClickListener { listener.invoke(TIME, getItem(adapterPosition)) }
                dateTv.setOnClickListener { listener.invoke(DATE, getItem(adapterPosition)) }
                root.setOnClickListener { listener.invoke(CLICK, getItem(adapterPosition)) }
            }
        }
    }

}