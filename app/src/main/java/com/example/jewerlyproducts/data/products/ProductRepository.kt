package com.example.jewerlyproducts.data.products

import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductsDao) {

    suspend fun addNewProduct(product: ProductsDataClass) {
        productDao.addProduct(product.toEntity())
    }

    fun getProductById(productId:Int):ProductsDataClass {
        return productDao.getProductById(productId).toDataClass()
    }

    fun getAllProducts(): Flow<List<ProductsDataClass>> {
        return productDao.getAllProducts().map { list ->
            list.sortedBy {
                it.name.lowercase()
            }.map {entity ->
                entity.toDataClass()
            }
        }
    }

    suspend fun updateProduct(product: ProductsDataClass) {
        productDao.updateProduct(product.toEntity())
    }

    fun deleteProduct(productId: Int) {
        productDao.deleteProduct(productId)
    }

}