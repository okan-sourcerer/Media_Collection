package com.example.mediacollection.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.mediacollection.R
import com.example.mediacollection.UtilHandler
import com.example.mediacollection.model.Content
import com.example.mediacollection.model.SAVE_CONTENT
import java.util.*

class ModifyActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editName: EditText
    private lateinit var editProducer: EditText
    private lateinit var editLinks: EditText

    private lateinit var radioGroup: RadioGroup

    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        imageView = findViewById(R.id.modifyImage)
        editName = findViewById(R.id.modifyName)
        editProducer = findViewById(R.id.modifyProducer)
        editLinks = findViewById(R.id.modifyLinks)

        radioGroup = findViewById(R.id.modifyTypeGroup)

        buttonSave = findViewById(R.id.modifyButtonSave)
        buttonCancel = findViewById(R.id.modifyButtonCancel)

        buttonSave.setOnClickListener {
            val name = editName.text.toString()
            val producer = editProducer.text.toString()
            val links = editLinks.text.toString()

            if (name != "" && producer != ""){
                val content = Content(null, name, producer, findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString().toUpperCase(
                    Locale.US
                ), links)
                UtilHandler.addContent(
                    content
                )
                setResult(SAVE_CONTENT)
                println("${content.name}, ${content.producer}, ${content.type}, ${content.links}")
            }
        }

    }
}