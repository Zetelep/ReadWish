package com.zulfa.readwish.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val htmlBook: String,

    val coverBook: String,

    val languages: List<String>,

    val bookshelves: List<String>,

    val subject: List<String>,

    val id: Int,

    val title: String,

    val authors: List<String>,

    val summaries: List<String>,

    val isFavorite: Boolean
): Parcelable