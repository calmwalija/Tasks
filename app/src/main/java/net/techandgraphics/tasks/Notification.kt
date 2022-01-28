package net.techandgraphics.tasks

import android.app.*
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import net.techandgraphics.tasks.broadcast.ReminderBroadcast
import net.techandgraphics.tasks.model.Task
import net.techandgraphics.tasks.ui.TaskActivity
import java.util.*


object Notification {
    const val id = "TaskNotificationId"
    const val name = "Reminder"
    const val extra = id


    private fun intent(context: Context) =
        Intent(context, TaskActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

    private fun pendingIntent(
        context: Context,
        task: Task,
        intent: Intent,
        activity: Boolean = true
    ): PendingIntent {
        return if (activity) PendingIntent.getActivity(
            context, task.id, intent, PendingIntent.FLAG_IMMUTABLE
        ) else PendingIntent.getBroadcast(
            context, task.id, intent, PendingIntent.FLAG_MUTABLE
        )
    }


    fun createNotification(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableVibration(true)
            notificationChannel.enableLights(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    fun onNotify(context: Context, task: Task) =
        NotificationCompat.Builder(context, id)
            .setSmallIcon(R.drawable.ic_icon)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(context, R.color.azure_blue))
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setDefaults(Notification.DEFAULT_ALL)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(task.description)
                    .setBigContentTitle(task.title)
                    .setSummaryText("Upcoming task in 20 mins")
            )
            .setContentText(task.description)
            .setContentTitle(task.title)
            .setContentIntent(pendingIntent(context, task, intent(context))).apply {
                NotificationManagerCompat.from(context).notify(task.id, build())
            }

    fun setReminderAlarm(context: Context, task: Task, callback: () -> Unit) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val bundle = Bundle()
        bundle.putParcelable(extra, task)
        val intent = Intent(context, ReminderBroadcast::class.java)
            .putExtra(extra, bundle)
        val pendingIntent = pendingIntent(context, task, intent, false)

        val calendar = Calendar.getInstance().apply {
            time = Date(task.timestamp)
            set(Calendar.MINUTE, (get(Calendar.MINUTE) - 20))
        }

        if (task.notify && calendar.after(Calendar.getInstance())) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else alarmManager.cancel(pendingIntent)
        callback.invoke()
    }

}