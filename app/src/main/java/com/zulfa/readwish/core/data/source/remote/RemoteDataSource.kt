package com.zulfa.readwish.core.data.source.remote

import android.util.Log
import com.zulfa.readwish.core.data.source.remote.network.ApiResponse
import com.zulfa.readwish.core.data.source.remote.network.ApiService
import com.zulfa.readwish.core.data.source.remote.response.BookItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllBook(): Flow<ApiResponse<List<BookItem>>>{
        //get data from api
        return flow{
            try {
                val response = apiService.getList()
                val dataArray = response.results
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                }else{
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString()) }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllBookByTopic(topic: String): Flow<ApiResponse<List<BookItem>>> {
        return flow {
            try {
                val response = apiService.getListByTopic(topic)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun search(query: String): Flow<ApiResponse<List<BookItem>>> {
        return flow {
            try {
                val response = apiService.search(query)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}