package com.zulfa.readwish.core.utils

import com.zulfa.readwish.core.data.source.local.entity.BookEntity
import com.zulfa.readwish.core.data.source.remote.response.BookItem
import com.zulfa.readwish.core.domain.model.Book

object DataMapper {

    fun mapResponsesToEntities(input: List<BookItem>): List<BookEntity>{
        val bookList = ArrayList<BookEntity>()
        input.map {
            val book = BookEntity(
                htmlBook = it.formats.textHtml,
                coverBook = it.formats.imageJpeg,
                languages = it.languages.joinToString(", "){it},
                bookshelves = it.bookshelves.joinToString(", "){it},
                subject = it.subject.joinToString(", ") {it},
                id = it.id,
                title = it.title,
                authors = it.authors.joinToString(", ") { author -> author.name },
                summaries = it.summaries.joinToString("% "){it},
                downloadCount = it.downloadCount,
                isFavorite = false
            )
            bookList.add(book)
        }

        return  bookList
    }

    fun mapEntitiesToDomain(input: List<BookEntity>): List<Book> =
        input.map {
            Book(
                htmlBook = it.htmlBook,
                coverBook = it.coverBook,
                languages = it.languages.split(",").map { lang -> lang.trim() },
                bookshelves = it.bookshelves.split(",").map { lang -> lang.trim() },
                subject = it.subject.split(",").map { lang -> lang.trim() },
                id = it.id,
                title = it.title,
                authors = it.authors.split(",").map { lang -> lang.trim() },
                summaries = it.summaries.split("%").map { lang -> lang.trim() },
                downloadCount = it.downloadCount,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Book)= BookEntity(
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
        isFavorite =input.isFavorite
    )

    fun mapResponsesToDomain(input: List<BookItem>): List<Book> =
        input.map {
            Book(
                htmlBook = it.formats.textHtml,
                coverBook = it.formats.imageJpeg,
                languages = it.languages,
                bookshelves = it.bookshelves,
                subject = it.subject,
                id = it.id,
                title = it.title,
                authors = it.authors.map { author -> author.name },
                summaries = it.summaries,
                downloadCount = it.downloadCount,
                isFavorite = false
            )
        }

}