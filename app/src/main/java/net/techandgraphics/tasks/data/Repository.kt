package net.techandgraphics.tasks.data

import kotlinx.coroutines.flow.Flow
import net.techandgraphics.tasks.db.Database
import net.techandgraphics.tasks.model.Task
import javax.inject.Inject

class Repository @Inject constructor(
    private val db: Database
) : Repo() {
    override suspend fun insert(task: Task) {
        db.dao.insert(task)
    }

    override suspend fun update(task: Task) {
        db.dao.update(task)
    }

    override suspend fun delete(task: Task) {
        db.dao.delete(task)
    }

    override val observeTask: Flow<List<Task>>
        get() = db.dao.observeTask()
}