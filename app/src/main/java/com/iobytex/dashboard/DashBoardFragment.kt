package com.iobytex.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.iobytex.task.R
import com.iobytex.task.databinding.FragmentDashboardBinding


class DashBoardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var dashBoardAdapter: DashBoardAdapter
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard,container,false)


        dashBoardAdapter = DashBoardAdapter(this)
        binding.pager.adapter =dashBoardAdapter

        TabLayoutMediator(binding.tabLayout,binding.pager){ tab,position ->
            when(position){
                0 -> {
                    tab.text= getText(R.string.today)
                }
                1 -> {
                    tab.text= getText(R.string.upcoming)
                }
                2 ->{
                    tab.text = getText(R.string.neglected)
                }
                3 -> {
                    tab.text= getText(R.string.taskDone)
                }
            }
        }


        binding.floatingbutton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_dashBoardFragment_to_task_option)
        }

        return binding.root
    }


}