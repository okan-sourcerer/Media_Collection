package com.example.mediacollection.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.mediacollection.R
import com.example.mediacollection.adapters.DetailFragmentAdapter
import com.example.mediacollection.model.POSITION
import com.example.mediacollection.model.TYPE
import com.example.mediacollection.utils.UtilHandler
import java.util.*

class ContentDetailActivity : AppCompatActivity() {

    private lateinit var pager: ViewPager2
    private lateinit var pagerAdapter: DetailFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_detail)

        val type = intent.getStringExtra(TYPE)
        val position = intent.getIntExtra(POSITION, 0)

        supportActionBar?.title = "${type!!.toLowerCase(Locale.US).capitalize(Locale.US)} Content"
        pager = findViewById(R.id.pager)
        pagerAdapter = DetailFragmentAdapter(supportFragmentManager, lifecycle, type, this)
        pager.adapter = pagerAdapter
        pager.setCurrentItem(position, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuDelete -> {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Delete Content")
                alertDialog.setMessage("Do you want to delete this content forever?")
                alertDialog.setPositiveButton("Delete"
                ) { _, _ ->
                    UtilHandler.getInstance(this@ContentDetailActivity)
                        .deleteContent(pagerAdapter.type, pager.currentItem)
                    pagerAdapter.notifyDataSetChanged()
                }
                alertDialog.setNegativeButton("Cancel"){ _, _ ->
                }
                alertDialog.show()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}