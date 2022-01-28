package net.techandgraphics.tasks.data

import kotlinx.coroutines.flow.Flow
import net.techandgraphics.tasks.model.Task

abstract class Repo {
    abstract suspend fun insert(task: Task)
    abstract suspend fun update(task: Task)
    abstract suspend fun delete(task: Task)
    abstract val observeTask: Flow<List<Task>>
}