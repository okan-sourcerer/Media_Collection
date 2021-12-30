package com.example.mediacollection.utils

import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.database.getStringOrNull
import androidx.core.net.toUri
import com.example.mediacollection.R
import com.example.mediacollection.model.*
import java.io.*
import java.lang.Exception
import java.util.*

class UtilHandler private constructor(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION){

    val SQL_CREATE = "create table if not exists $TABLE_NAME ($TABLE_CONTENT_NAME TEXT NOT NULL," +
            " $TABLE_PRODUCER TEXT NOT NULL, $TABLE_TYPE TEXT NOT NULL, $TABLE_LINKS TEXT, $TABLE_IMAGE TEXT)"

    companion object : SingletonHolder<UtilHandler, Context>(::UtilHandler)

    // List of the items in the

    val contents: MutableList<Content> = mutableListOf()

    init {
        populateList()
    }


    //TODO: Make an edit and create activity. +
    // Make user to be able to select image from gallery ++
    // Store user data. ++
    // OPTIONAL: Add settings menu to modify colors of the app and store them as well. 


    // Name of the categories
        val categories: List<Category> = listOf(
            Category(ALL, R.drawable.all2, "All Content Types"),
            Category(BOOK, R.drawable.ic_book, "Books"),
            Category(MOVIE, R.drawable.ic_movie, "Movies"),
            Category(MUSIC, R.drawable.ic_music, "Musics"),
            Category(VIDEO, R.drawable.ic_video, "Videos")
        )

    /**
     * This method requires a filter word. Filter needs to be one of the words declared in Constants.kt
     * Returns a list of contents which are of required type
     */
    fun getContent(filter: String): List<Content>{
        return if(filter == ALL) contents else contents.filter { it.type == filter }
    }

    // add new content to the list. This will be changed to handle new images as well
    fun addContent(content: Content, bitmap: Bitmap?){
        var imagePath: String? = null
        if (bitmap != null){
            val fileName = UUID.randomUUID().toString()
            imagePath = saveToStorage(bitmap, fileName)
            println("image path: $imagePath")
        }

        val contentValues = ContentValues()

        contentValues.put(TABLE_CONTENT_NAME, content.name)
        contentValues.put(TABLE_PRODUCER, content.producer)
        contentValues.put(TABLE_TYPE, content.type)
        contentValues.put(TABLE_LINKS, content.links)
        contentValues.put(TABLE_IMAGE, imagePath)

        // set new image path to the content and add it into contents
        if (imagePath == null) content.image = null else content.image = Uri.parse(imagePath)
        contents.add(content)
        // insert the content to database
        writableDatabase.insert(TABLE_NAME, null, contentValues)
    }

    // save the image to internal storage with randomly generated name. Returns image path
    private fun saveToStorage(bitmap: Bitmap, filename: String): String{
        val cw = ContextWrapper(context.applicationContext)
        val directory = cw.getDir("images", Context.MODE_PRIVATE) // get dir to store image
        val path = File(directory, filename)
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(path)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        } catch (e: Exception){
            e.printStackTrace()
        }finally {
            outputStream?.close()
        }
        return directory.absolutePath + File.separator + filename
    }

    fun loadImage(path: String): Bitmap?{
        var bitmap: Bitmap?
        try {
            val file = File(path)
            bitmap = BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: FileNotFoundException){
            return null // file does not exists.
        }
        return bitmap
    }

    // when opening the app the first time, fetch all the content from database
    private fun populateList(){
        val SQL_ALL = "select * from $TABLE_NAME"
        val cursor = readableDatabase.rawQuery(SQL_ALL, null)

        if (cursor.moveToFirst()){
            var name: String
            var producer: String
            var type: String
            var links: String?
            var imagePath: String?
            while (!cursor.isAfterLast){
                name = cursor.getString(0)
                producer = cursor.getString(1)
                type = cursor.getString(2)
                links = cursor.getStringOrNull(3)
                imagePath = cursor.getStringOrNull(4)
                contents.add(Content(if(imagePath != null) Uri.parse(imagePath) else null, name, producer, type, links ?: ""))
                cursor.moveToNext()
            }
        }

        cursor.close()
    }

    override fun onCreate(p0: SQLiteDatabase?) {

        p0!!.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }


}
