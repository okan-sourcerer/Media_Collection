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
import com.example.mediacollection.model.*
import com.example.mediacollection.utils.UtilHandler

class ContentFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContentAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var contents: List<Content>
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        type = requireArguments().getString(TYPE, ALL)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)

        handleUI(view, type)

        return view
    }

    override fun onResume() {
        super.onResume()
        adapter.contents = UtilHandler.getInstance(requireContext()).getContent(type)
        adapter.notifyDataSetChanged()
    }

    private fun handleUI(view: View, type:String){

        recyclerView = view.findViewById(R.id.categoryRecycler)

        contents = UtilHandler.getInstance(view.context).getContent(type)
        adapter = ContentAdapter(contents, type)
        layoutManager = LinearLayoutManager(view.context)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}