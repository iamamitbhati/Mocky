package com.iamamitbhati.mocky.repository

import com.iamamitbhati.mocky.data.domain.toEntity
import com.iamamitbhati.mocky.data.local.dao.ProductDao
import com.iamamitbhati.mocky.data.remote.ProductRemoteData
import com.iamamitbhati.mocky.model.ProductDetail
import com.iamamitbhati.mocky.model.ProductResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteData: ProductRemoteData,
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineContext
) : ProductRepository {

    override suspend fun getAllProducts(): Flow<Resource<List<ProductDetail>>> {

        return object : NetworkBoundResource<List<ProductDetail>, ProductResponse>() {
            override suspend fun fetchFromNetwork(): Response<ProductResponse> {
                return productRemoteData.getAllProducts()
            }

            override suspend fun saveNetworkResult(response: ProductResponse) {
                productDao.insertProductList(response.list.toEntity())
            }

            override fun shouldFetch(resultType: List<ProductDetail>): Boolean {
                return resultType.isEmpty()
            }

            override fun getList(): List<ProductDetail> {
                return productDao.getProductList()
            }

        }.asFlow().flowOn(ioDispatcher)
    }

    override suspend fun getProductDetail(id: Long): Flow<ProductDetail> {
        return withContext(ioDispatcher) {
            flowOf(productDao.getProductDetail(id))
        }
    }

    override suspend fun setFavoriteUnFavorite(id: Long, isFav: Boolean) {
        CoroutineScope(ioDispatcher).launch {
            productDao.setFavoriteUnFavorite(id, isFav)
        }

    }

    override suspend fun getAllFavorite(): Flow<List<ProductDetail>> {
        return withContext(ioDispatcher) {
            flowOf(productDao.getAllFavoriteList())
        }

    }
}