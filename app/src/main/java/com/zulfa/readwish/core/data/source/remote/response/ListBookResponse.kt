package com.zulfa.readwish.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListBookResponse(

    @field:SerializedName("next")
    val next: String,

    @field:SerializedName("previous")
    val previous: Any,

    @field:SerializedName("count")
    val count: Int,

    @field:SerializedName("results")
    val results: List<BookItem>
)

data class Formats(

    @field:SerializedName("text/html")
    val textHtml: String,

    @field:SerializedName("image/jpeg")
    val imageJpeg: String,
)

data class BookItem(

    @field:SerializedName("formats")
    val formats: Formats,

    @field:SerializedName("languages")
    val languages: List<String>,

    @field:SerializedName("bookshelves")
    val bookshelves: List<String>,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("subjects")
    val subject:List<String>,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("authors")
    val authors: List<AuthorsItem>,

    @field:SerializedName("summaries")
    val summaries: List<String>,

    )

data class AuthorsItem(

    @field:SerializedName("name")
    val name: String, // format namanya kaya sitasi

    )