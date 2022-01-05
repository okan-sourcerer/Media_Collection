package com.example.mediacollection.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mediacollection.R
import com.example.mediacollection.utils.UtilHandler

class SettingsActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        button = findViewById(R.id.settingsButton)

        button.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Wipe All Data")
            alertDialog.setMessage("All data will be wiped. Are you sure?")
            alertDialog.setPositiveButton("Wipe") { _, _ ->
                UtilHandler.getInstance(this).wipeAllData()
            }
            alertDialog.setNegativeButton("Cancel") { _, _ -> }
            alertDialog.show()
        }
    }
}