package com.iobytex.option

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.zxing.client.android.BeepManager
import com.iobytex.di.TaskModelMoshi
import com.iobytex.domain.Result
import com.iobytex.extensions.previewFlow
import com.iobytex.task.R
import com.iobytex.task.databinding.FragmentScanTaskBinding
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class ScanTaskFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @Inject internal lateinit var beepManager: BeepManager
    internal lateinit var binding:FragmentScanTaskBinding
    @TaskModelMoshi @Inject internal lateinit var moshi: Moshi
    @Inject internal lateinit var ioDispatcher: CoroutineDispatcher
    private val scanTaskViewModel: ScanTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_scan_task, container, false)


        viewLifecycleOwner.lifecycleScope.launch {
            binding.barcodeScanner.previewFlow(beepManager=beepManager)
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { result ->
                    scanTaskViewModel.decodeScan(result)
                }

            scanTaskViewModel.scanResult.collect { task ->
                when(task){
                    is Result.Success -> {
                        //TODO: Pass the task object to fill task fragment
                        Navigation.createNavigateOnClickListener(ScanTaskFragmentDirections.actionScanTaskFragmentToFillTaskFragment(taskWithTypes = task.value))
                    }
                    is Result.Error -> {
                        Timber.d(task.exception)
                    }
                    else -> {
                        Timber.d("Unknown Type")
                    }
                }
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.setNavigationOnClickListener {
            dismiss()
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }
}
