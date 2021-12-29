package com.example.mediacollection.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import com.example.mediacollection.R

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



    }
}