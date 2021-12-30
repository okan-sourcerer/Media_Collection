package com.example.mediacollection.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mediacollection.utils.UtilHandler
import com.example.mediacollection.fragments.ContentFragment
import com.example.mediacollection.model.*

class ContentsFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val context: Context):
        FragmentStateAdapter(fragmentManager, lifecycle) {

    private lateinit var type: String

    override fun getItemCount(): Int{
        return UtilHandler.getInstance(context).categories.size
    }

    override fun createFragment(position: Int): Fragment {
        type = when(position){
            1 -> BOOK
            2 -> MOVIE
            3-> MUSIC
            4 -> VIDEO
            else -> ALL
        }
        return ContentFragment(UtilHandler.getInstance(context).getContent(type), type)
    }
}