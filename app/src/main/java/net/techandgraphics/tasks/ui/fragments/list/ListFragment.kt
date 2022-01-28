package net.techandgraphics.tasks.ui.fragments.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.techandgraphics.tasks.Notification
import net.techandgraphics.tasks.R
import net.techandgraphics.tasks.SwipeDecorator
import net.techandgraphics.tasks.databinding.FragmentListBinding
import net.techandgraphics.tasks.ext.Ext
import net.techandgraphics.tasks.model.Task
import net.techandgraphics.tasks.ui.fragments.TaskViewModel
import net.techandgraphics.tasks.vo.TimeDate
import java.util.*


@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var bind: FragmentListBinding
    private lateinit var adapter: ListFragmentAdapter
    private val viewModel: TaskViewModel by viewModels()


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            ListFragmentDirections.actionMainFragmentToSettingsFragment().also {
                findNavController().navigate(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun callbacks(listener: Listener, task: Task) {
        when (listener) {
            Listener.NOTIFICATION -> notifyListener(task)
            Listener.TIME -> timeListener(task)
            Listener.DATE -> dateListener(task)
            Listener.CLICK -> ListFragmentDirections
                .actionMainFragmentToTaskFragment(task).also {
                    findNavController().navigate(it)
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind = FragmentListBinding.bind(view)
        adapter = ListFragmentAdapter { p0, p1 -> callbacks(p0, p1) }.also { bind.adapter = it }
        viewModel.observeTasks.observe(viewLifecycleOwner) {
            bind.top.isVisible = it.isEmpty().not()
            bind.empty.isVisible = it.isEmpty()
            adapter.submitList(it)
        }
        onItemTouchHelper()
        addTask()
        viewModel.viewModelScope.launch {
            viewModel.taskChannel.collectLatest {
                if (it is TaskViewModel.Callback.OnSuccess) Notification
                    .setReminderAlarm(requireContext(), it.task!!) {
                    }
            }
        }
    }

    private fun dateListener(task: Task) =
        TimeDate.datePickerDialog(
            requireActivity(),
            Calendar.getInstance().apply { time = Date(task.timestamp) }
        ) { viewModel.update(task.copy(timestamp = it.timeInMillis)) }.show()


    private fun timeListener(task: Task) =
        TimeDate.timePickerDialog(
            requireActivity(),
            Calendar.getInstance().apply { time = Date(task.timestamp) }
        ) { viewModel.update(task.copy(timestamp = it.timeInMillis)) }

    private fun notifyListener(task: Task) = viewModel.update(task.copy(notify = !task.notify))


    private fun addTask() = bind.fab.setOnClickListener {
        ListFragmentDirections.actionMainFragmentToTaskFragment().also {
            findNavController().navigate(it)
        }
    }

    private fun onItemTouchHelper() =
        object : SwipeDecorator(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = adapter.currentList[viewHolder.adapterPosition]
                if (direction == 8) {
                    viewModel.update(task.copy(archive = true))
                    Ext.snackbar(requireView(), "Task Archived", task) {
                        viewModel.update(task.copy(archive = false))
                    }
                } else {
                    viewModel.delete(task)
                    Ext.snackbar(requireView(), "Task Deleted", task) {
                        viewModel.onCreate(it, true)
                    }
                }
            }
        }.also { ItemTouchHelper(it).attachToRecyclerView(bind.recyclerView) }


}