package net.techandgraphics.tasks.ui.fragments.task

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.techandgraphics.tasks.Notification
import net.techandgraphics.tasks.R
import net.techandgraphics.tasks.databinding.FragmentTaskBinding
import net.techandgraphics.tasks.ext.Ext
import net.techandgraphics.tasks.model.Task
import net.techandgraphics.tasks.ui.fragments.TaskViewModel
import net.techandgraphics.tasks.vo.Color
import net.techandgraphics.tasks.vo.TimeDate
import java.util.*

@AndroidEntryPoint
class TaskFragment : Fragment(R.layout.fragment_task) {

    private lateinit var bind: FragmentTaskBinding
    private lateinit var colorAdapter: ColorItemAdapter
    private val viewModel: TaskViewModel by viewModels()
    private var task = Task(title = "", description = "")
    private val args: TaskFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind = FragmentTaskBinding.bind(view)

        task = if (args.task == null) task else args.task!!
        var dateTime = Calendar.getInstance().apply { time = Date(task.timestamp) }

        bind.taskBind = task


        with(bind) {
            colorAdapter = ColorItemAdapter(task.color) {
                task.color = it
                create.background.setTint(ContextCompat.getColor(requireContext(), it))
                backBtn.background.setTint(ContextCompat.getColor(requireContext(), it))
            }.also {
                adapter = it
            }

            fab.isVisible = dateTime.after(Calendar.getInstance()) && task.complete.not()
            colorAdapter.submitList(Color.color) {
                colorAdapter.onClick.invoke(task.color)
            }

            fab.setOnClickListener {
                viewModel.update(
                    task.copy(
                        complete = !task.complete,
                        color = Color.color[0]
                    )
                )

                Ext.snackbar(requireView(), "Task marked as completed", task) {}

            }

            title.setText(task.title)
            title.requestFocus()
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), HORIZONTAL, false)

            reminder.setOnClickListener {
                task.notify = !task.notify
                Ext.reminderCompoundDrawables(reminder, task.notify)
            }

            viewModel.viewModelScope.launch { collectCallback() }

            backBtn.setOnClickListener { requireActivity().onBackPressed() }

            time.setOnClickListener {
                TimeDate.timePickerDialog(requireActivity(), dateTime) {
                    dateTime = it
                    time.text = TimeDate.getTime(it.timeInMillis)
                }
            }

            date.setOnClickListener {
                TimeDate.datePickerDialog(
                    requireActivity(),
                    Calendar.getInstance().apply { time = Date(task.timestamp) }
                ) {
                    dateTime = it
                    date.text = TimeDate.getDate(it.timeInMillis)
                }.show()
            }

            create.setOnClickListener {
                viewModel.onCreate(
                    task.copy(
                        title = title.text.toString().trim(),
                        description = description.text.toString().trim(),
                        timestamp = dateTime.timeInMillis
                    ), args.task == null
                )
            }
        }
    }


    private suspend fun collectCallback() =
        viewModel.taskChannel.collectLatest {
            when (it) {
                is TaskViewModel.Callback.OnError -> when (it.ofType) {
                    TaskViewModel.OfType.TITLE -> {
                        Ext.toast(requireContext(), "Please input task title.")
                        bind.title.requestFocus()
                    }
                    TaskViewModel.OfType.DESCRIPTION -> {
                        Ext.toast(requireContext(), "Please input description.")
                        bind.description.requestFocus()
                    }
                    else -> {}
                }
                is TaskViewModel.Callback.OnSuccess ->
                    Notification.setReminderAlarm(requireContext(), it.task!!) {
                        requireActivity().onBackPressed()
                    }
            }
        }

}