package com.zulfa.readwish.core.utils

import com.zulfa.readwish.core.data.source.local.entity.BookEntity
import com.zulfa.readwish.core.data.source.remote.response.BookItem
import com.zulfa.readwish.core.domain.model.Book


object DataMapper {

    fun mapResponsesToEntities(input: List<BookItem>): List<BookEntity> {
        val bookList = ArrayList<BookEntity>()
        input.map {
            val book = BookEntity(
                htmlBook = it.formats.textHtml ?: "No Link",
                coverBook = it.formats.imageJpeg ?: "",
                languages = it.languages?.joinToString(", ") ?: "Unknown",
                bookshelves = it.bookshelves?.joinToString(", ") ?: "Unknown",
                subject = it.subject?.joinToString(", ") ?: "Unknown",
                id = it.id,
                title = it.title ?: "Untitled",
                authors = it.authors?.joinToString(", ") { author -> author.name ?: "Unknown" } ?: "Unknown",
                summaries = it.summaries?.joinToString("% ") ?: "No summary",
                downloadCount = it.downloadCount ?: 0,
                isFavorite = false
            )
            bookList.add(book)
        }

        return bookList
    }

    fun mapEntitiesToDomain(input: List<BookEntity>): List<Book> =
        input.map {
            Book(
                htmlBook = it.htmlBook,
                coverBook = it.coverBook,
                languages = it.languages.split(",").map { lang -> lang.trim() },
                bookshelves = it.bookshelves.split(",").map { shelf -> shelf.trim() },
                subject = it.subject.split(",").map { sub -> sub.trim() },
                id = it.id,
                title = it.title,
                authors = it.authors.split(",").map { author -> author.trim() },
                summaries = it.summaries.split("%").map { summary -> summary.trim() },
                downloadCount = it.downloadCount,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Book) = BookEntity(
        htmlBook = input.htmlBook,
        coverBook = input.coverBook,
        languages = input.languages.joinToString(","),
        bookshelves = input.bookshelves.joinToString(","),
        subject = input.subject.joinToString(","),
        id = input.id,
        title = input.title,
        authors = input.authors.joinToString(","),
        summaries = input.summaries.joinToString("%"),
        downloadCount = input.downloadCount,
        isFavorite = input.isFavorite
    )

    fun mapResponsesToDomain(input: List<BookItem>): List<Book> =
        input.map {
            Book(
                htmlBook = it.formats.textHtml ?: "No Link",
                coverBook = it.formats.imageJpeg ?: "",
                languages = it.languages ?: listOf("Unknown"),
                bookshelves = it.bookshelves ?: listOf("Unknown"),
                subject = it.subject ?: listOf("Unknown"),
                id = it.id,
                title = it.title ?: "Untitled",
                authors = it.authors?.map { author -> author.name ?: "Unknown" } ?: listOf("Unknown"),
                summaries = it.summaries ?: listOf("No summary"),
                downloadCount = it.downloadCount ?: 0,
                isFavorite = false
            )
        }
}
