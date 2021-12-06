package com.iobytex.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.iobytex.domain.Result
import com.iobytex.task.R
import com.iobytex.task.databinding.FragmentTaskDoneBinding
import com.iobytex.utils.Utils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TaskDoneFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var binding: FragmentTaskDoneBinding
    private val taskViewModel : TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_task_done, container, false)



        val tasksAdapter = TasksAdapter(clickListener = TaskItemClickListener{ view,taskWithTypes,flow ->
            when(flow){
                Utils.TaskFlow.Edit -> {
                    view.findNavController().navigate(DashBoardFragmentDirections.actionDashBoardFragmentToFillTaskFragment(taskWithTypes))
                }
                Utils.TaskFlow.Undo -> {
                    //TODO:(Pass the id to viewModel to insert false into completed column)
                }
                Utils.TaskFlow.Delete -> {

                }
                else ->{

                }
            }
        })
        binding.taskDoneRecycleView.adapter =tasksAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            taskViewModel.taskDone
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { value ->
                    when(value){
                        is  Result.Loading -> {

                        }
                        is Result.Success -> {
                            tasksAdapter.submitList(value.value)
                        }
                        is Result.Error -> {

                        }
                    }
                }
        }

        return  binding.root

    }


}