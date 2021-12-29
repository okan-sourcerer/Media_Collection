package com.example.mediacollection.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.mediacollection.R
import com.example.mediacollection.adapters.DetailFragmentAdapter
import com.example.mediacollection.model.POSITION
import com.example.mediacollection.model.TYPE

class ContentDetailActivity : AppCompatActivity() {

    private lateinit var pager: ViewPager2
    private lateinit var pagerAdapter: DetailFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_detail)

        val type = intent.getStringExtra(TYPE)
        val position = intent.getIntExtra(POSITION, 0)
        pager = findViewById(R.id.pager)
        pagerAdapter = DetailFragmentAdapter(supportFragmentManager, lifecycle, type!!)
        pager.adapter = pagerAdapter
        pager.setCurrentItem(position, false)
        //val fm = supportFragmentManager
        //fm.beginTransaction().add(R.id.detailContainer, DetailFragment(type, position)).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting_menu -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()

            else -> super.onOptionsItemSelected(item)
        }

        return true
    }
}