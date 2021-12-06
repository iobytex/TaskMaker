package com.iobytex.option

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iobytex.domain.Result
import com.iobytex.task.R
import com.iobytex.task.databinding.FragmentTaskOptionBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskOptionFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val taskOptionViewModel : TaskOptionViewModel by viewModels()
    private lateinit var binding: FragmentTaskOptionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_task_option,container,false)


        val manager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.recyleTask.layoutManager = manager

        val selectTaskAdapter = TaskOptionAdapter(InputTaskListener{ view, optionId ->
            when(optionId){
                0 -> {
                    view.setOnClickListener {
                        view.findNavController().navigate(TaskOptionFragmentDirections.actionOptionTaskFragmentToFillTaskFragment())
                    }
                }
                1 -> {
                    view.setOnClickListener {
                        view.findNavController().navigate(TaskOptionFragmentDirections.actionOptionTaskFragmentToScanTaskFragment())
                    }
                }
            }
        })
        binding.recyleTask.adapter = selectTaskAdapter

        lifecycleScope.launch {
             taskOptionViewModel.optionTaskType
                 .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                 .collect {
                    when(it) {
                        is Result.Success -> {
                            selectTaskAdapter.submitList(it.value)
                        }
                        else -> { }
                    }
             }

        }



        return binding.root
    }


    companion object {
        const val TAG = "AddNewTaskFragment"
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }


}