package net.techandgraphics.tasks.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import net.techandgraphics.tasks.Notification
import net.techandgraphics.tasks.model.Task

class ReminderBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.getBundleExtra(Notification.extra)
            ?.getParcelable<Task>(Notification.extra)?.let { task ->
                context?.let { Notification.onNotify(it, task) }
            }

    }

}