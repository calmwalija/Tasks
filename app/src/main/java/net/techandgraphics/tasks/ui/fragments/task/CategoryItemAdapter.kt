package net.techandgraphics.tasks.ui.fragments.task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import net.techandgraphics.tasks.databinding.ColorItemBinding
import net.techandgraphics.tasks.model.Category
import net.techandgraphics.tasks.ui.fragments.task.CategoryItemAdapter.ViewHolder
import net.techandgraphics.tasks.databinding.FragmentListCategoryItemBinding as Binding

class CategoryItemAdapter(
    val color:Int,
    val onClick: (Int) -> Unit
) : ListAdapter<Category, ViewHolder>(ListDiffUtil.DiffUtil) {

    private var selectedColor = color

    private fun View.scale(float: Float) {
        scaleX = float
        scaleY = float
    }


     inner class ViewHolder(
        private val bind: Binding
    ) : RecyclerView.ViewHolder(bind.root) {
         fun bind(category: Category) = bind.apply {
            categoryBind = category
            icon.background.setTint(ContextCompat.getColor(root.context, category.backgroundColor))

//            if (selectedColor == color) root.scale(1.8f) else {
//                root.scale(1f)
//            }

        }

        init {
            bind.root.setOnClickListener {
                onClick.invoke(getItem(adapterPosition).backgroundColor)
                selectedColor = currentList[adapterPosition].backgroundColor
             }

        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(Binding.inflate(LayoutInflater.from(parent.context)))
    }


}