package com.example.mediacollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediacollection.adapters.ContentAdapter
import com.example.mediacollection.R
import com.example.mediacollection.model.Content

class ContentFragment(val contents: List<Content>, private val type: String): Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContentAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)

        handleUI(view)

        return view
    }

    private fun handleUI(view: View){

        recyclerView = view.findViewById(R.id.categoryRecycler)

        adapter = ContentAdapter(contents, type)
        layoutManager = LinearLayoutManager(view.context)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}