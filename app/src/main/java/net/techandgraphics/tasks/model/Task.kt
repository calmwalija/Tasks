package net.techandgraphics.tasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import net.techandgraphics.tasks.vo.Utils

@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val location: String? = null,
    val priority: Boolean = false,
    var notify: Boolean = true,
    val archive: Boolean = false,
    val complete: Boolean = false,
    var color: Int = Utils.color[0],
    val reminderTimestamp: Long = System.currentTimeMillis(),
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable
