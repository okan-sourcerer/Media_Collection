package com.example.mediacollection.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.example.mediacollection.R
import com.example.mediacollection.utils.UtilHandler
import com.example.mediacollection.adapters.ContentsFragmentAdapter
import com.example.mediacollection.model.CATEGORY
import com.example.mediacollection.model.CONTENT_CREATE
import com.example.mediacollection.model.SAVE_CONTENT
import com.google.android.material.tabs.TabLayout
import java.util.*

class ContentActivity : AppCompatActivity() {

    private lateinit var tab: TabLayout
    private lateinit var pager: ViewPager2
    private lateinit var pagerAdapter: ContentsFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val fm = supportFragmentManager

        tab = findViewById(R.id.contentTab)
        tab.setBackgroundColor(resources.getColor(R.color.red, null))

        // add all the categories in the category list
        for (ca in UtilHandler.getInstance(this).categories){
            tab.addTab(tab.newTab().setIcon(ca.typeImage).setContentDescription(ca.name))
        }

        pager = findViewById(R.id.contentPager)
        pagerAdapter = ContentsFragmentAdapter(fm, lifecycle, this)
        pager.adapter = pagerAdapter


        // check the intent. create fragment according to user clicks
        if (intent != null){
            val position = intent.getIntExtra(CATEGORY,0)
            pager.setCurrentItem(position, false)
            tab.selectTab(tab.getTabAt(position))
        }
        /*
        else{ // not likely but just in case
            fm.beginTransaction().add(R.id.fragmentContainer,
                    ContentFragment()).commit()
        }
        */

        // handle tab select actions
        tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // get content list by filtering based on tabs
                // replace current fragment to new tab fragment
                pager.setCurrentItem(tab!!.position, true)

            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // when page changes, change current tab as well
        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tab.selectTab(tab.getTabAt(position))
                supportActionBar?.title = UtilHandler.getInstance(this@ContentActivity)
                    .categories[position].description
            }
        })
    }

    // create menu with add and settings option
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_menu, menu)
        return true
    }

    // handle menu items behaviours
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add -> {
                val intent = Intent(this, ModifyActivity::class.java)
                launchActivity.launch(intent)
            }
            R.id.menu_setting -> Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()

            else -> super.onOptionsItemSelected(item)
        }

        return true
    }

    // launches modify activity to create a new content. Waits for a result to update adapters.
    private val launchActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if (result.resultCode == SAVE_CONTENT){
            pagerAdapter.notifyDataSetChanged()
            intent.putExtra(CONTENT_CREATE, SAVE_CONTENT) // when we are here, we notify fragment with intent to reload the dataset
        }
    }

    override fun onResume() {
        super.onResume()
        pagerAdapter.notifyDataSetChanged()
    }
}
