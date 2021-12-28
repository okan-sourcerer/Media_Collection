package com.example.mediacollection.model

data class Category(
    var name: String, // name of the category
    var typeImage: Int, // representing image id. can be null
    var description: String
)