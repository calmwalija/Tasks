package net.techandgraphics.tasks.db

import androidx.room.Database
import androidx.room.RoomDatabase
import net.techandgraphics.tasks.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    companion object {
        const val DB_NAME = "task_repo"
    }

    abstract val dao: Dao

}