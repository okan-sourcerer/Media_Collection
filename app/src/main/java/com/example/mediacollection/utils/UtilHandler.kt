package com.example.mediacollection.utils

import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.database.getStringOrNull
import com.example.mediacollection.R
import com.example.mediacollection.model.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class UtilHandler private constructor(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION){

    private val SQL_CREATE = "create table if not exists $TABLE_NAME ($TABLE_UNIQUE_ID TEXT PRIMARY KEY, $TABLE_CONTENT_NAME TEXT NOT NULL," +
            " $TABLE_PRODUCER TEXT NOT NULL, $TABLE_TYPE TEXT NOT NULL, $TABLE_LINKS TEXT, $TABLE_IMAGE TEXT)"

    companion object : SingletonHolder<UtilHandler, Context>(::UtilHandler)

    private val contents: MutableList<Content> = mutableListOf()

    init {
        populateList()
    }
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

        content.databaseId = UUID.randomUUID().toString()
        val contentValues = ContentValues()

        contentValues.put(TABLE_UNIQUE_ID, content.databaseId)
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)
        } catch (e: Exception){
            e.printStackTrace()
        }finally {
            outputStream?.close()
        }
        return directory.absolutePath + File.separator + filename
    }

    // when opening the app the first time, fetch all the content from database
    private fun populateList(){
        val SQL_ALL = "select * from $TABLE_NAME"
        val cursor = readableDatabase.rawQuery(SQL_ALL, null)

        if (cursor.moveToFirst()){
            var databaseId: String
            var name: String
            var producer: String
            var type: String
            var links: String?
            var imagePath: String?
            while (!cursor.isAfterLast){
                databaseId = cursor.getString(0)
                name = cursor.getString(1)
                producer = cursor.getString(2)
                type = cursor.getString(3)
                links = cursor.getStringOrNull(4)
                imagePath = cursor.getStringOrNull(5)
                contents.add(Content(databaseId, if(imagePath != null) Uri.parse(imagePath) else null, name, producer, type, links ?: ""))
                cursor.moveToNext()
            }
        }

        cursor.close()
    }

    fun updateContent(content: Content, imageChanged: Boolean, bitmap: Bitmap?){
        val values = ContentValues()
        values.put(TABLE_CONTENT_NAME, content.name)
        values.put(TABLE_PRODUCER, content.producer)
        values.put(TABLE_TYPE, content.type)
        values.put(TABLE_LINKS, content.links)
        var imagePath: String? = content.image?.path
        if (imageChanged){ // our image is different. we dont have old image. we have to update
            if (bitmap != null){ // new image exists
                if (imagePath != null){
                    imagePath = Uri.parse(imagePath).lastPathSegment
                }
                val filename: String = imagePath ?: UUID.randomUUID().toString()

                imagePath = saveToStorage(bitmap, filename)
                println("image path: $imagePath")
                content.image = Uri.parse(imagePath)
            } else{ // new image does not exists. need to delete old image and set this to null
                imagePath = null
                deleteImage(content.image!!.path!!)
                content.image = null
            }
        }
        values.put(TABLE_IMAGE, imagePath)
        writableDatabase.update(TABLE_NAME, values, "ID=?", arrayOf(content.databaseId))
    }

    fun deleteContent(type: String, position: Int){
        val content = getContent(type)[position]
        contents.removeAll {  // remove item from the list
            content.databaseId == it.databaseId
        }
        writableDatabase.delete(TABLE_NAME, "ID=?", arrayOf(content.databaseId))
    }

    private fun deleteImage(path: String){//delete image at that path
        val file = File(path)
        if (file.exists()){
            file.delete()
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {

        p0!!.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun wipeAllData() { // delete all contents from the database and from our list
        // select all image paths and delete them from our files.
        val cursor:Cursor = readableDatabase.rawQuery("select * from $TABLE_NAME", null)
        if(cursor.moveToFirst()){
            var imagePath: String?
            while (!cursor.isAfterLast){

                imagePath = cursor.getStringOrNull(5) // table row of our imagepaths
                if (imagePath != null){
                    deleteImage(imagePath)
                }
            }
        }
        cursor.close()

        writableDatabase.delete(TABLE_NAME, "", arrayOf())
        contents.removeAll { true }
    }
}
