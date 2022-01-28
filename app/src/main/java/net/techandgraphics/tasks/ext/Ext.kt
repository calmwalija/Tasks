package net.techandgraphics.tasks.ext

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.snackbar.Snackbar
import net.techandgraphics.tasks.R
import net.techandgraphics.tasks.model.Task

object Ext {


    fun snackbar(view: View, message: String, task: Task, callback: (Task) -> Unit) =
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).also {
            it.setAction("undo") { callback.invoke(task) }
        }.show()

    fun toast(context: Context, message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()


    fun completeCompoundDrawables(view: AppCompatTextView, complete: Boolean) {
        view.setCompoundDrawablesWithIntrinsicBounds(
            if (complete) R.drawable.ic_task_complete else 0,
            0,
            0,
            0
        )
    }

    fun reminderCompoundDrawables(view: AppCompatTextView, notify: Boolean) {
        view.setCompoundDrawablesWithIntrinsicBounds(
            0,
            if (notify) R.drawable.ic_notifications_on else R.drawable.ic_notifications_off,
            0,
            0
        )
    }
}
