package com.example.mediacollection.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.mediacollection.R
import com.example.mediacollection.UtilHandler
import com.example.mediacollection.adapters.ContentsFragmentAdapter
import com.example.mediacollection.fragments.ContentFragment
import com.example.mediacollection.model.ALL
import com.example.mediacollection.model.CATEGORY
import com.google.android.material.tabs.TabLayout

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
        for (ca in UtilHandler.categories){
            tab.addTab(tab.newTab().setIcon(ca.typeImage).setContentDescription(ca.name))
        }

        pager = findViewById(R.id.contentPager)
        pagerAdapter = ContentsFragmentAdapter(fm, lifecycle)
        pager.adapter = pagerAdapter


        // check the intent. create fragment according to user clicks
        if (intent != null){
            val position = intent.getIntExtra(CATEGORY,0)
            pager.setCurrentItem(position, false)
            tab.selectTab(tab.getTabAt(position))

            /*
            val category: String = tab.getTabAt(position)!!.contentDescription.toString()
            // create fragment
            fm.beginTransaction().add(R.id.fragmentContainer,
                    ContentFragment(UtilHandler.getContent(category))).commit()
            // set tab position
            tab.selectTab(tab.getTabAt(position))

             */
        }
        else{ // not likely but just in case
            fm.beginTransaction().add(R.id.fragmentContainer,
                    ContentFragment(UtilHandler.getContent(ALL), ALL)).commit()
        }

        // handle tab select actions
        tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // get content list by filtering based on tabs
                // replace current fragment to new tab fragment
                pager.setCurrentItem(tab!!.position, true)
                //fm.beginTransaction().replace(R.id.fragmentContainer, ContentFragment(UtilHandler.getContent(tab!!.contentDescription.toString()))).commit()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tab.selectTab(tab.getTabAt(position))
            }
        })
    }
}
