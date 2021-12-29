package com.example.mediacollection.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mediacollection.UtilHandler
import com.example.mediacollection.fragments.ContentFragment
import com.example.mediacollection.model.*

class ContentsFragmentAdapter(private val fragmentManager: FragmentManager, private val lifecycle: Lifecycle):
        FragmentStateAdapter(fragmentManager, lifecycle) {

    private lateinit var type: String

    override fun getItemCount(): Int{
        return UtilHandler.categories.size
    }

    override fun createFragment(position: Int): Fragment {
        type = when(position){
            1 -> BOOK
            2 -> MOVIE
            3-> MUSIC
            4 -> VIDEO
            else -> ALL
        }
        println("$position: ${type}")
        return ContentFragment(UtilHandler.getContent(type), type)
    }
}