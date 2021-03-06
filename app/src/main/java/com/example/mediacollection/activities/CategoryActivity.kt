package com.example.mediacollection.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediacollection.R
import com.example.mediacollection.adapters.CategoryAdapter
import com.example.mediacollection.utils.UtilHandler

class CategoryActivity: AppCompatActivity() {

    // This activity will only show other categories inside a recyclerview. User can select the category that they want
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        recycler = findViewById(R.id.categoryRecycler)
        adapter = CategoryAdapter(UtilHandler.getInstance(this).categories)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting_menu -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}