package com.example.jewerlyproducts.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jewerlyproducts.data.expenses.ExpensesDao
import com.example.jewerlyproducts.data.expenses.ExpensesEntity
import com.example.jewerlyproducts.data.materials.MaterialsDao
import com.example.jewerlyproducts.data.materials.MaterialsEntity
import com.example.jewerlyproducts.data.relations.ProductMaterialsCrossRef
import com.example.jewerlyproducts.data.products.ProductsDao
import com.example.jewerlyproducts.data.products.ProductsEntity
import com.example.jewerlyproducts.data.sells.SellDao
import com.example.jewerlyproducts.data.sells.SellEntity

@Database(
    entities = [MaterialsEntity::class, ProductsEntity::class, ProductMaterialsCrossRef::class, SellEntity::class, ExpensesEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val expensesDao: ExpensesDao
    abstract val materialsDao: MaterialsDao
    abstract val productsDao: ProductsDao
    abstract val sellDao: SellDao
}