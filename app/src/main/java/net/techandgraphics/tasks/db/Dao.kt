package net.techandgraphics.tasks.db

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import net.techandgraphics.tasks.model.Task

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task ORDER BY timestamp")
    fun observeTask(): Flow<List<Task>>


}