package com.example.mediacollection.adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mediacollection.fragments.DetailFragment
import com.example.mediacollection.model.POSITION
import com.example.mediacollection.model.TYPE
import com.example.mediacollection.utils.UtilHandler

class DetailFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val type: String, private val context: Context): FragmentStateAdapter(fragmentManager, lifecycle) {

    // get all the category count
    override fun getItemCount() = UtilHandler.getInstance(context).getContent(type).size

    override fun createFragment(position: Int): Fragment {
        // return all if position == 0. else filter for the specific type
        val bundle = Bundle(2)
        bundle.putString(TYPE, type)
        bundle.putInt(POSITION, position)
        val fragment = DetailFragment()
        fragment.arguments = bundle
        return fragment// ContentFragment(if(position == 0) UtilHandler.contents else UtilHandler.contents.filter { it.type == position })
    }
}