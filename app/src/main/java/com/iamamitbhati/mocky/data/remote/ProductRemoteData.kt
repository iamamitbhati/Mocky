package com.iamamitbhati.mocky.data.remote

import com.iamamitbhati.mocky.data.network.Service
import com.iamamitbhati.mocky.model.ProductResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * This class would handle all the remote api calls
 */
class ProductRemoteData @Inject constructor(private val service: Service) {

    suspend fun getAllProducts(): Response<ProductResponse> {
        return service.getAllProducts()
    }
}