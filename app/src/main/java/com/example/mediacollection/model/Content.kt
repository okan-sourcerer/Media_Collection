package com.example.mediacollection.model

data class Content(
    var image: Int?, // image id of the content
    var name: String, // name
    var producer: String, // can be important person for the content
    var type: String, // type (book, movie, music, video)
    var links: List<String> = listOf()
)