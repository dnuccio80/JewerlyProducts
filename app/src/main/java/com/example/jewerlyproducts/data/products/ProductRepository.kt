package com.example.jewerlyproducts.data.products

import com.example.jewerlyproducts.data.relations.ProductMaterialsCrossRef
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductsDao) {

    suspend fun addNewProduct(product: ProductsDataClass) {
        productDao.addProduct(product.toEntity())
    }

    fun getProductById(productName:String):ProductsDataClass {
        return productDao.getProductById(productName).toDataClass()
    }

    fun getAllProducts(): Flow<List<ProductsDataClass>> {
        return productDao.getAllProducts().map { list ->
            list.sortedBy {
                it.productName.lowercase()
            }.map {entity ->
                entity.toDataClass()
            }
        }
    }

    suspend fun updateProduct(product: ProductsDataClass) {
        productDao.updateProduct(product.toEntity())
    }

    fun deleteProduct(productName: String) {
        productDao.deleteProduct(productName)
    }

    suspend fun addProductMaterialCrossRef(crossRef:ProductMaterialsCrossRef) {
        productDao.addProductMaterialsCrossRef(crossRef)
    }

    suspend fun deleteProductMaterialCrossRef(productName:String) {
        productDao.deleteProductMaterialCrossRef(productName)
    }

//    suspend fun getProductWithMaterials(productId: Int):ProductWithMaterials {
//       return productDao.getProductWithMaterials(productId)
//    }

}