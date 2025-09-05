package com.example.jewerlyproducts.data.products

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM productsentity")
    fun getAllProducts():Flow<List<ProductsEntity>>

    @Query("SELECT * FROM productsentity WHERE id = :productId")
    fun getProductById(productId: Int): ProductsEntity

    @Insert
    suspend fun addProduct(product: ProductsEntity)

    @Update
    suspend fun updateProduct(product: ProductsEntity)

    @Query("DELETE FROM ProductsEntity WHERE id = :productId")
    fun deleteProduct(productId: Int)

}