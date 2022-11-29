package com.iamamitbhati.mocky.data.domain

import com.iamamitbhati.mocky.model.ProductDetail
import com.iamamitbhati.mocky.model.ProductData

fun List<ProductData>.toEntity(): List<ProductDetail> {
    return this.map { product ->
        ProductDetail(
            id = product.id,
            title = product.title,
            imageURL = product.imageURL,
            favorite = product.isFav,
            ratingCount = product.ratingCount ?: 0f,
            totalReviewCount = product.totalReviewCount ?: 0,
            price = product.saleUnitPrice ?: 0f
        )
    }
}