package com.iamamitbhati.mocky.data.network

import com.iamamitbhati.mocky.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("2f06b453-8375-43cf-861a-06e95a951328")
    suspend fun getAllProducts(): Response<ProductResponse>
}