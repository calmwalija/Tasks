package net.techandgraphics.tasks.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.techandgraphics.tasks.data.Repo
import net.techandgraphics.tasks.model.Task
import net.techandgraphics.tasks.vo.Utils
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {


    private val channel = Channel<Callback>()
    val taskChannel = channel.receiveAsFlow()


    fun onCreate(task: Task, flag: Boolean = false) {
        when {
            task.title.isBlank() -> viewModelScope.launch { channel.send(Callback.OnError(OfType.TITLE)) }
            task.description.isBlank() ->
                viewModelScope.launch { channel.send(Callback.OnError(OfType.DESCRIPTION)) }
            else -> viewModelScope.launch {
                if (!flag) repo.update(task) else repo.insert(task)
                channel.send(Callback.OnSuccess(task = task))
            }
        }
    }

    fun delete(task: Task) = viewModelScope.launch { repo.delete(task) }
    fun update(task: Task, callback: Boolean = true) = viewModelScope.launch {
        val calendar = Calendar.getInstance().apply {
            time = Date(task.timestamp)
        }.before(Calendar.getInstance())
        repo.update(
            task.copy(
                complete = calendar, color = if (calendar) Utils.color[0] else task.color
            )
        )
        if (callback)
            channel.send(Callback.OnSuccess(task))
    }

    sealed class Callback(val ofType: OfType? = null, val task: Task? = null) {
        class OnError(ofType: OfType) : Callback(ofType)
        class OnSuccess(task: Task) : Callback(task = task)
    }

    val observeTasks = repo.observeTask.asLiveData()

    enum class OfType {
        TITLE, DESCRIPTION
    }


}