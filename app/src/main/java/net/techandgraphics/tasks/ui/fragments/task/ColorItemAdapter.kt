package net.techandgraphics.tasks.ui.fragments.task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.techandgraphics.tasks.databinding.ColorItemBinding
import net.techandgraphics.tasks.ui.fragments.task.ColorItemAdapter.ViewHolder
import net.techandgraphics.tasks.vo.Color

class ColorItemAdapter(
    val color:Int,
    val onClick: (Int) -> Unit
) : ListAdapter<Int, ViewHolder>(ListDiffUtil.DiffUtil) {

    private var selectedColor = color

    private fun View.scale(float: Float) {
        scaleX = float
        scaleY = float
    }


    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(
        private val bind: ColorItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(color: Int) = bind.apply {
            colorSchema.background.setTint(ContextCompat.getColor(root.context, color))

            if (selectedColor == color) root.scale(1.8f) else {
                root.scale(1f)
            }

        }

        init {
            bind.root.setOnClickListener {
                onClick.invoke(getItem(adapterPosition))
                selectedColor = currentList[adapterPosition]
                notifyDataSetChanged()
            }

        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ColorItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


}