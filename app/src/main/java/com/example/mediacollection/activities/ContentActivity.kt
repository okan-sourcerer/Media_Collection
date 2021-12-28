package com.example.mediacollection.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mediacollection.R
import com.example.mediacollection.UtilHandler
import com.example.mediacollection.fragments.ContentFragment
import com.example.mediacollection.model.ALL
import com.example.mediacollection.model.CATEGORY
import com.example.mediacollection.model.Content
import com.google.android.material.tabs.TabLayout

class ContentActivity : AppCompatActivity() {

    private lateinit var tab: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val fm = supportFragmentManager

        tab = findViewById(R.id.contentTab)
        tab.setBackgroundColor(resources.getColor(R.color.red, null))

        // add all the categories in the category list
        for (ca in UtilHandler.categories){
            tab.addTab(tab.newTab().setIcon(ca.typeImage).setContentDescription(ca.name))
        }

        // check the intent. create fragment according to user clicks
        val intent = intent
        if (intent != null){
            val position = intent.getIntExtra(CATEGORY,0)
            val category: String = tab.getTabAt(position)!!.contentDescription.toString()
            // create fragment
            fm.beginTransaction().add(R.id.fragmentContainer,
                    ContentFragment(UtilHandler.getContent(category))).commit()
            // set tab position
            tab.selectTab(tab.getTabAt(position))
        }
        else{ // not likely but just in case
            fm.beginTransaction().add(R.id.fragmentContainer,
                    ContentFragment(UtilHandler.getContent(ALL))).commit()
        }

        // handle tab select actions
        tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // get content list by filtering based on tabs
                // replace current fragment to new tab fragment
                fm.beginTransaction().replace(R.id.fragmentContainer, ContentFragment(UtilHandler.getContent(tab!!.contentDescription.toString()))).commit()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}
