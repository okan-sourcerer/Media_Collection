package com.example.mediacollection.model

import android.net.Uri

data class Content(
    var databaseId: String?,
    var image: Uri?, // image id of the content
    var name: String, // name
    var producer: String, // can be important person for the content
    var type: String, // type (book, movie, music, video)
    var links: String = ""
)