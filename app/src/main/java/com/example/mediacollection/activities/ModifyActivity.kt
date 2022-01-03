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
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mediacollection.R
import com.example.mediacollection.model.*
import com.example.mediacollection.utils.UtilHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Suppress("DEPRECATION")
class ModifyActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var editName: EditText
    private lateinit var editProducer: EditText
    private lateinit var editLinks: EditText

    private lateinit var radioGroup: RadioGroup

    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private var changedImage = false
    private var fromDetail = false

    private lateinit var detailContent: Content

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

        supportActionBar?.title = "Create Content"

        // check if we are here to edit the content
        if (intent != null){

            if (intent.getStringExtra(TYPE) != null){
                fromDetail = true
                val type = intent.getStringExtra(TYPE)!!
                val position = intent.getIntExtra(POSITION, 0)
                fromDetail(type, position)
            }
        }

        buttonSave.setOnClickListener {
            val name = editName.text.toString()
            val producer = editProducer.text.toString()
            val links = editLinks.text.toString()

            if (name != "" && producer != ""){

                if (fromDetail){
                    setResult(UPDATE_CONTENT)
                    detailContent.name = name
                    detailContent.producer = producer
                    detailContent.links = links
                    detailContent.type = findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString().toUpperCase(Locale.US)
                    UtilHandler.getInstance(this).updateContent(detailContent, changedImage, if(changedImage && imageView.drawable != null)(imageView.drawable as BitmapDrawable).bitmap else null)
                }else{
                    val content = Content(null, null, name, producer, findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString().toUpperCase(
                        Locale.US
                    ), links)
                    // save this content to our list and check whether user picked an image or not. if yes, save it
                    UtilHandler.getInstance(this).addContent(content, if(changedImage)(imageView.drawable as BitmapDrawable).bitmap else null)
                    println("$changedImage")
                    setResult(SAVE_CONTENT)
                    println("${content.name}, ${content.producer}, ${content.type}, ${content.links}")
                }
            }
        }

        imageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            launchActivity.launch(intent)
        }

        // clear imageview on long click
        imageView.setOnLongClickListener {
            changedImage = true
            imageView.setImageDrawable(null)
            true
        }
    }

    // inserts the content from detail fragment. sets content values to the views
    private fun fromDetail(type: String, position: Int){

        // get content
        detailContent = UtilHandler.getInstance(this).getContent(type)[position]
        supportActionBar?.title = "Update ${detailContent.type.toLowerCase(Locale.US).capitalize(Locale.US)}"
        if (detailContent.image != null){
            CoroutineScope(Dispatchers.Main).launch {
                //content.image?.let { imageView.setImageURI(it)}// instantiate image from uri
                imageView.setImageURI(detailContent.image)
            }
        }

        editName.text.insert(0, detailContent.name)
        editProducer.text.insert(0, detailContent.producer)
        editLinks.text.insert(0, detailContent.links)

        when(detailContent.type){
            BOOK -> radioGroup.check(R.id.modifyBook)
            MOVIE -> radioGroup.check(R.id.modifyMovie)
            MUSIC -> radioGroup.check(R.id.modifyMusic)
            else -> radioGroup.check(R.id.modifyVideo)
        }

        buttonSave.setText(R.string.update) // change text of the button to say "Update"
    }

    private val launchActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if (result.resultCode == RESULT_OK && result.data != null){
            val uri: Uri = result.data!!.data!!
            val bitmap: Bitmap
            if (Build.VERSION.SDK_INT < 28){
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri) // this is deprecated but there is no other options

            }else{ // this method only works api >= 28

                val source = ImageDecoder.createSource(contentResolver, uri)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
            changedImage = true
            imageView.setImageBitmap(bitmap)
        }
    }
}