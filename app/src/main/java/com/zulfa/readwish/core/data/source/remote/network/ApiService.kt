package com.zulfa.readwish.core.data.source.remote.network

import com.zulfa.readwish.core.data.source.remote.response.ListBookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("books")
    suspend fun getList(): ListBookResponse

    @GET("books")
    suspend fun getListByTopic(@Query("topic") topic: String, @Query("sort") sort: String): ListBookResponse

    @GET("books")
    suspend fun search(@Query("search") query: String): ListBookResponse
    }