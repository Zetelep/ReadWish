package com.zulfa.readwish.core.di

import androidx.room.Room
import com.zulfa.readwish.core.data.BookRepository
import com.zulfa.readwish.core.data.source.local.LocalDataSource
import com.zulfa.readwish.core.data.source.local.room.BookDatabase
import com.zulfa.readwish.core.data.source.remote.RemoteDataSource
import com.zulfa.readwish.core.data.source.remote.network.ApiService
import com.zulfa.readwish.core.domain.repository.IBookRepository
import com.zulfa.readwish.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<BookDatabase>().bookDao() }
    single {
        Room.databaseBuilder(
                androidContext(),
                BookDatabase::class.java, "Book.db"
            ).fallbackToDestructiveMigration(false).build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit =  Retrofit.Builder()
            .baseUrl("https://gutendex.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IBookRepository> { BookRepository(get(), get(), get()) }
}