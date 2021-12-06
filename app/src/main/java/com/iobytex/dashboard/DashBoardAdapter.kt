package com.iobytex.dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DashBoardAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TodayFragment()
            1 -> UpcomingFragment()
            2 -> NeglectedFragment()
            else -> TaskDoneFragment()
        }
    }
}