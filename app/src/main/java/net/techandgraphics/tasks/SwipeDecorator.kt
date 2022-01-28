package net.techandgraphics.tasks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import net.techandgraphics.tasks.R


abstract class SwipeDecorator(private val context: Context) :
    ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT
    ) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addSwipeLeftActionIcon(R.drawable.ic_delete)
            .addSwipeLeftLabel("Delete")
            .addBackgroundColor(Color.TRANSPARENT)

            .addSwipeRightActionIcon(R.drawable.ic_archive)
            .addSwipeRightLabel("Archive")
            .addBackgroundColor(Color.TRANSPARENT)


//            .setSwipeLeftLabelColor(MaterialColors.getColor(recyclerView, R.attr.swipeTextColor))


            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}