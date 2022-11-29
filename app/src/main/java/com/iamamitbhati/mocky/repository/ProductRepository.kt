package com.iamamitbhati.mocky.repository

import com.iamamitbhati.mocky.model.ProductDetail
import kotlinx.coroutines.flow.Flow

/**
 * Single entry point for repository
 */
interface ProductRepository {
    suspend fun getAllProducts(): Flow<Resource<List<ProductDetail>>>

    suspend fun getProductDetail(id: Long): Flow<ProductDetail>

    suspend fun setFavoriteUnFavorite(id: Long, isFav: Boolean)

    suspend fun getAllFavorite():  Flow<List<ProductDetail>>
}