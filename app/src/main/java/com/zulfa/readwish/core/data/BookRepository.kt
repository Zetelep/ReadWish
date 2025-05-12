package com.zulfa.readwish.core.data

import com.zulfa.readwish.core.data.source.local.LocalDataSource
import com.zulfa.readwish.core.data.source.remote.RemoteDataSource
import com.zulfa.readwish.core.data.source.remote.network.ApiResponse
import com.zulfa.readwish.core.data.source.remote.response.BookItem
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.core.domain.repository.IBookRepository
import com.zulfa.readwish.core.utils.AppExecutors
import com.zulfa.readwish.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class BookRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IBookRepository {
    override fun getAllBook(): Flow<Resource<List<Book>>> =
        object : NetworkBoundResource<List<Book>,List<BookItem>>(){
            override fun loadFromDB(): Flow<List<Book>> {
                return localDataSource.getAllBook().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<BookItem>>>  =
                remoteDataSource.getAllBook()


            override suspend fun saveCallResult(data: List<BookItem>) {
                val bookList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertBook(bookList)
            }

            override fun shouldFetch(data: List<Book>?): Boolean = data.isNullOrEmpty()

        }.asFlow()

    override fun getAllBookByTypes(topic: String,sort:String): Flow<Resource<List<Book>>> =
        object : NetworkBoundResource<List<Book>,List<BookItem>>(){
            override fun loadFromDB(): Flow<List<Book>> {
                return localDataSource.getBooksByTypesSorted(topic,sort).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<BookItem>>>  =
                remoteDataSource.getAllBookByTopic(topic,sort)


            override suspend fun saveCallResult(data: List<BookItem>) {
                val bookList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertBook(bookList)
            }
            override fun shouldFetch(data: List<Book>?): Boolean = true
//                data.isNullOrEmpty()
        }.asFlow()


    override fun searchBooks(query: String): Flow<Resource<List<Book>>> =
        remoteDataSource.search(query).map { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapResponsesToDomain(response.data)
                    Resource.Success(data)
                }
                is ApiResponse.Empty -> Resource.Success(emptyList())
                is ApiResponse.Error -> Resource.Error(response.errorMessage)
            }
        }.onStart {
            emit(Resource.Loading())
        }.flowOn(Dispatchers.IO)

    override fun searchBooksByTopic(query: String): Flow<Resource<List<Book>>> =
        remoteDataSource.searchBooksByTopic(query).map { response ->
            when (response) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapResponsesToDomain(response.data)
                    Resource.Success(data)
                }
                is ApiResponse.Empty -> Resource.Success(emptyList())
                is ApiResponse.Error -> Resource.Error(response.errorMessage)
            }
        }.onStart {
            emit(Resource.Loading())
        }.flowOn(Dispatchers.IO)

    override fun getFavoriteBook(): Flow<List<Book>> {
        return localDataSource.getFavoriteBook().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteBook(book: Book, state: Boolean) {
        val bookEntity = DataMapper.mapDomainToEntity(book)
        appExecutors.diskIO().execute{
            localDataSource.setFavoriteBook(bookEntity,state)
        }
    }

    override suspend fun saveBook(book: Book) {
        val entity = DataMapper.mapDomainToEntity(book)
        localDataSource.insertBook(listOf(entity))
    }
}