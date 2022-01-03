package com.example.mediacollection.model

// constants for different categories
const val ALL = "ALL"
const val BOOK = "BOOK"
const val MOVIE = "MOVIE"
const val MUSIC = "MUSIC"
const val VIDEO = "VIDEO"

// constant for passing intents
const val CATEGORY = "CATEGORY"

// constants for creating new content in ModifyActivity.kt
const val CONTENT_CREATE = "CREATE"
const val SAVE_CONTENT = 15

const val UPDATE_CONTENT = 55

// instead of using other methods, we will use these 2 constants to pass information to ContentDetailActivity
// with type, we will filter the list and with position, we will get the wanted content.
// We can not delete items on the main menu. That is why we are certain of their position
const val TYPE = "TYPE"
const val POSITION = "POSITION"

const val DATABASE_NAME = "content.db"
const val VERSION = 1

const val TABLE_NAME = "Content"
const val TABLE_CONTENT_NAME = "name"
const val TABLE_PRODUCER = "producer"
const val TABLE_TYPE = "type"
const val TABLE_LINKS = "links"
const val TABLE_IMAGE = "image"

const val TABLE_UNIQUE_ID = "ID"