package com.example.mediacollection.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mediacollection.R
import com.example.mediacollection.utils.UtilHandler
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
    private var changedImage = false

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
                // save this content to our list and check whether user picked an image or not. if yes, save it
                UtilHandler.getInstance(this).addContent(content, if(changedImage)(imageView.drawable as BitmapDrawable).bitmap else null)
                println("$changedImage")
                setResult(SAVE_CONTENT)
                println("${content.name}, ${content.producer}, ${content.type}, ${content.links}")
            }
        }

        imageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launchActivity.launch(intent)
        }

    }

    private val launchActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if (result.resultCode == RESULT_OK && result.data != null){
            val uri: Uri = result.data!!.data!!
            val bitmap: Bitmap
            if (Build.VERSION.SDK_INT < 28){
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

            }else{ // this method only works api >= 28

                val source = ImageDecoder.createSource(contentResolver, uri)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
            changedImage = true
            imageView.setImageBitmap(bitmap)
        }
    }
}