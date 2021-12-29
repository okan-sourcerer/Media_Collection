package com.example.mediacollection.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mediacollection.UtilHandler
import com.example.mediacollection.fragments.DetailFragment

class DetailFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val type: String): FragmentStateAdapter(fragmentManager, lifecycle) {

    // get all the category count
    override fun getItemCount() = UtilHandler.getContent(type).size

    override fun createFragment(position: Int): Fragment {
        // return all if position == 0. else filter for the specific type
        return DetailFragment(type, position)// ContentFragment(if(position == 0) UtilHandler.contents else UtilHandler.contents.filter { it.type == position })
    }
}