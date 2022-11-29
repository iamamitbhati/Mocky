package com.iamamitbhati.mocky.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iamamitbhati.mocky.model.ProductDetail

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductList(productList: List<ProductDetail>)

    @Query("SELECT * FROM ${ProductDetail.TABLE_NAME}")
    fun getProductList(): List<ProductDetail>

    @Query("SELECT * FROM ${ProductDetail.TABLE_NAME} where id=:id")
    fun getProductDetail(id: Long): ProductDetail

    @Query("UPDATE ${ProductDetail.TABLE_NAME} SET favorite=:isFav WHERE id = :id")
    fun setFavoriteUnFavorite(id: Long, isFav: Boolean)

    @Query("SELECT * FROM ${ProductDetail.TABLE_NAME} WHERE favorite = 1")
    fun getAllFavoriteList(): List<ProductDetail>
}