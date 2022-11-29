
package com.iamamitbhati.mocky.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iamamitbhati.mocky.model.ProductDetail.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ProductDetail(
    @PrimaryKey
    val id: Long,
    val imageURL: String?,
    val title: String?,
    val price: Float,
    val ratingCount: Float,
    val totalReviewCount: Int,
    var favorite: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "product_entity"
    }
}
