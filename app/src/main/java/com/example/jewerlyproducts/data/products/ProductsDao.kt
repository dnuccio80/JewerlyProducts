package com.example.jewerlyproducts.data.products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.jewerlyproducts.data.relations.MaterialWithQuantity
import com.example.jewerlyproducts.data.relations.ProductMaterialsCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM productsentity")
    fun getAllProducts():Flow<List<ProductsEntity>>

    @Query("SELECT * FROM productsentity WHERE productName = :productName")
    fun getProductById(productName: String): ProductsEntity

    @Insert
    suspend fun addProduct(product: ProductsEntity)

    @Update
    suspend fun updateProduct(product: ProductsEntity)

    @Query("DELETE FROM ProductsEntity WHERE productName = :productName")
    fun deleteProduct(productName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductMaterialsCrossRef(crossRef: ProductMaterialsCrossRef)

    @Query("DELETE FROM ProductMaterialsCrossRef WHERE productName = :productName")
    suspend fun deleteProductMaterialCrossRef(productName: String)

    @Transaction
    @Query("SELECT * FROM ProductMaterialsCrossRef WHERE productName = :productName")
    suspend fun getProductMaterialCrossRef(productName: String):List<ProductMaterialsCrossRef>

}