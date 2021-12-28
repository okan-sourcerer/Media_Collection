package com.example.mediacollection.model

// constants for different categories
val ALL = "ALL"
val BOOK = "BOOK"
val MOVIE = "MOVIE"
val MUSIC = "MUSIC"
val VIDEO = "VIDEO"

// constant for passing intents
val CATEGORY = "CATEGORY"
val CATEGORY_INTENT = 15

// instead of using other methods, we will use these 2 constants to pass information to ContentDetailActivity
// with type, we will filter the list and with position, we will get the wanted content.
// We can not delete items on the main menu. That is why we are certain of their position
val TYPE = "TYPE"
val POSITION = "POSITION"