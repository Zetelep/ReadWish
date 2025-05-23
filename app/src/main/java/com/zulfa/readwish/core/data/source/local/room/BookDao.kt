package com.zulfa.readwish.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zulfa.readwish.core.data.source.local.entity.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getAllBook(): Flow<List<BookEntity>>

    @Query("""
        SELECT * FROM book 
        WHERE subject LIKE '%' || :type || '%' 
           OR bookshelves LIKE '%' || :type || '%'
        ORDER BY 
            CASE WHEN :sort = 'ascending' THEN id END ASC,
            CASE WHEN :sort = 'descending' THEN id END DESC,
            CASE WHEN :sort = 'popular' THEN download_count END DESC
    """)
    fun getBooksByTypesSorted(type: String, sort: String): Flow<List<BookEntity>>


    @Query("SELECT * FROM book WHERE isFavorite = 1")
    fun getFavoriteBook(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book:List<BookEntity>)

    @Update
    fun updateFavoriteBook(book: BookEntity)
}