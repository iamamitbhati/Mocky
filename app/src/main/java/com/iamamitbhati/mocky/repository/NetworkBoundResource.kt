package com.iamamitbhati.mocky.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asFlow() = flow<Resource<ResultType>>{
        emit(Resource.Loading())
        val list = getList()
        if (shouldFetch(list)) {
            //emit(Resource.Loading())
            val apiResponse = fetchFromNetwork()
            if (apiResponse.isSuccessful) {
                processResponse(apiResponse)?.let { saveNetworkResult(it) }
                emitAll(flowOf(getList()).map {
                    Resource.Success(it)
                })
            } else {
                emit(Resource.Failed(apiResponse.message()))
            }
        } else {
            emitAll(flowOf(getList()).map { Resource.Success(it) })
        }
    }
    @WorkerThread
    protected open fun processResponse(response: Response<RequestType>) = response.body()

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(resultType: ResultType): Boolean

    @MainThread
    protected abstract fun getList(): ResultType

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): Response<RequestType>
}