package net.techandgraphics.tasks.vo

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object TimeDate {

    private val calendarInstance = Calendar.getInstance()

    private fun hourOfDay(calendar: Calendar) = calendar.get(Calendar.HOUR_OF_DAY)
    private fun minuteOfHour(calendar: Calendar) = calendar.get(Calendar.MINUTE)
    private fun monthOfYear(calendar: Calendar) = calendar.get(Calendar.MONTH)
    private fun dayOfMonth(calendar: Calendar) = calendar.get(Calendar.DAY_OF_MONTH)
    private fun year(calendar: Calendar) = calendar.get(Calendar.YEAR)


    fun timePickerDialog(
        context: Context,
        calendar: Calendar = calendarInstance,
        listener: (Calendar) -> Unit
    ) {
        val picker = MaterialTimePicker.Builder()
            .setHour(hourOfDay(calendar))
            .setMinute(minuteOfHour(calendar))
            .build()
        picker.show((context as AppCompatActivity).supportFragmentManager, null)
        picker.addOnPositiveButtonClickListener {
            calendar.apply {
                set(Calendar.HOUR_OF_DAY, picker.hour)
                set(Calendar.MINUTE, picker.minute)
            }.also { listener.invoke(it) }
        }
    }


    fun datePickerDialog(
        context: Context,
        calendar: Calendar = calendarInstance,
        listener: (Calendar) -> Unit
    ) = DatePickerDialog(context, { _, year, monthOfYear, dayOfMonth ->
        calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }.also { listener.invoke(it) }
    }, year(calendar), monthOfYear(calendar), dayOfMonth(calendar))


    fun getDate(date: Long): String =
        SimpleDateFormat("EE, dd MMM", Locale.ENGLISH).format(Date(date))


    fun getTime(date: Long) =
        DateFormat.getTimeInstance(DateFormat.SHORT).format(Date(date)).lowercase()


//    private fun daysBetween(dateTime: DateTime) =
//        Days.daysBetween(
//            dateTime.toLocalDate(),
//            DateTime(calendarInstance.timeInMillis).toLocalDate()
//        ).days


//    fun getDate(timeInMillis: Long): String = when (daysBetween(DateTime(timeInMillis))) {
//        1 -> "Yesterday"
//        0 -> "Today"
//        -1 -> "Tomorrow"
//        else -> formatDate(timeInMillis)
//
//    }

}