package com.example.mediacollection.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediacollection.R
import com.example.mediacollection.UtilHandler
import com.example.mediacollection.adapters.CategoryAdapter

class CategoryActivity: AppCompatActivity() {

    // This activity will only show other categories inside a recyclerview. User can select the category that they want
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        recycler = findViewById(R.id.categoryRecycler)
        adapter = CategoryAdapter(UtilHandler.categories)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }
}