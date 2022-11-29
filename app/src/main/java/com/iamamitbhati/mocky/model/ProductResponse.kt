package com.iamamitbhati.mocky.model

import com.google.gson.annotations.SerializedName


data class ProductResponse(@SerializedName("products") val list: List<ProductData>)

data class ProductData(
    val id: Long,
    val imageURL: String?,
    val title: String?,
    val ratingCount: Float?,
    val saleUnitPrice: Float?,
    val totalReviewCount: Int?,
    var isFav: Boolean = false
)

