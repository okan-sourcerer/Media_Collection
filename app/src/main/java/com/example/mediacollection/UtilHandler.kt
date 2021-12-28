package com.example.mediacollection

import com.example.mediacollection.model.*

object UtilHandler{

    // List of the items in the
    val contents: List<Content> = listOf(
            Content(R.drawable.black_hole, "Interstellar", "Christopher Nolan", MOVIE),
            Content(R.drawable.black_hole, "Stellar", "Oan Nolan", MOVIE),
            Content(R.drawable.black_hole, "Some basic romance", "A producer", MOVIE),
            Content(R.drawable.black_hole, "Interstellar", "Christopher Nolan", VIDEO),
            Content(null, "In Stellar", "Chris topper Nolan", MOVIE),
            Content(R.drawable.batman_begins, "Interstellar", "Christopher Nolan", MOVIE),
            Content(R.drawable.black_hole, "Dune", "Dunekirk", MOVIE),
            Content(R.drawable.black_hole, "Interstellar", "Christopher Nolan", BOOK),
            Content(null, "Interstellar", "Christopher Nolan", MOVIE),
            Content(R.drawable.black_hole, "Interstellar", "Christopher Nolan", BOOK),
            Content(R.drawable.black_hole, "Batman Begins", "Christopher Nolan", MOVIE),
            Content(R.drawable.black_hole, "Andromeda", "Lxst Cxntury", MUSIC),
            Content(null, "Interstellar", "Christopher Nolan", MOVIE),
            Content(R.drawable.black_hole, "Dune", "Dunekirk", MOVIE),
            Content(R.drawable.black_hole, "Interstellar", "Christopher Nolan", BOOK),
            Content(null, "Interstellar", "Christopher Nolan", MOVIE),
            Content(R.drawable.black_hole, "Interstellar", "Christopher Nolan", BOOK),
            Content(R.drawable.black_hole, "Batman Begins", "Christopher Nolan", MOVIE),
            Content(R.drawable.black_hole, "Andromeda", "Lxst Cxntury", MUSIC),
            Content(null, "Interstellar", "Christopher Nolan", MOVIE),
            Content(R.drawable.black_hole, "Batman BeginsThis method requires a filter word. Filter needs to be one of the words declared in Constants.kt\n" +
                    "     * Returns a list of contents which are of required typThis method requires a filter word. Filter needs to be one of the words declared in Constants.kt\n" +
                    "     * Returns a list of contents which are of required typThis method requires a filter word. Filter needs to be one of the words declared in Constants.kt\n" +
                    "     * Returns a list of contents which are of required typThis method requires a filter word. Filter needs to be one of the words declared in Constants.kt\n" +
                    "     * Returns a list of contents which are of required typ", "Christopher Nolan", MOVIE)
    )

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
}
