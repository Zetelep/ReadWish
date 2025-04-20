package com.zulfa.readwish.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("html_book")
    val htmlBook: String,

    @ColumnInfo("cover_book")
    val coverBook: String,

    @ColumnInfo("languages")
    val languages: String,

    @ColumnInfo("bookshelves")
    val bookshelves: String,

    @ColumnInfo("subject")
    val subject: String,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("authors")
    val authors: String,

    @ColumnInfo("summaries")
    val summaries: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)
