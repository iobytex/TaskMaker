package com.iobytex.option

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.iobytex.domain.Result
import com.iobytex.task.R
import com.iobytex.task.databinding.FragmentFillTaskBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class FillTaskFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding:FragmentFillTaskBinding
    private val args : FillTaskFragmentArgs by navArgs()

    @Inject
    lateinit var fillTaskViewModelFactory: FillTaskViewModelFactory


    private val fillTaskViewModel: FillTaskViewModel by viewModels{
        FillTaskViewModel.provideFactory(fillTaskViewModelFactory,args.taskWithTypes)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fill_task, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                args.taskWithTypes?.let {
                    binding.topAppBar.title = "Edit Task"
                }
            }
        }

        binding.fillTaskViewModel =  fillTaskViewModel
        binding.lifecycleOwner = this

        val taskColorTypeAdapter = TaskColorTypeAdapter(binding.colorType)

        binding.colorType.adapter = taskColorTypeAdapter

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                val colorJob = launch{
                    fillTaskViewModel.colorPalette
                        .collect {
                            when (it) {
                                is Result.Success -> {
                                    taskColorTypeAdapter.submitList(it.value)
                                }
                                is Result.Loading -> {

                                }
                                is Result.Error -> {

                                }
                            }
                        }
                }

                val placeJob =  launch {

                    val placeActivityResult =  registerForActivityResult(
                        ActivityResultContracts.StartActivityForResult()
                    ){ result -> {

                        result
                    }

                    }

                    //placeActivityResult.launch()

                }



            }

        }


        //TODO: ===> connect filltaskVM to ui and design ui


        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.setNavigationOnClickListener {
            dismiss()
        }
        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                 R.id.save -> {
                     if(args.taskWithTypes !== null){
                         //TODO: update db
                     }else{
                         //TODO: save to db
                     }
                     true
                 }
                else -> false
            }
        }


    }
}