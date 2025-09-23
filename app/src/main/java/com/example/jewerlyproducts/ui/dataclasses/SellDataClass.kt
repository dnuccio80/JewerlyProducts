package com.example.jewerlyproducts.ui.dataclasses

import androidx.room.Embedded
import com.example.jewerlyproducts.data.sells.SellEntity

data class SellDataClass(
    val sellId:Int = 0,
    val product: ProductsDataClass,
    val quantity: Int
) {
    fun toEntity():SellEntity {
        return SellEntity(
            sellId = sellId,
            product = product.toEntity(),
            quantity = quantity
        )
    }
}
