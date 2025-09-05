package com.example.jewerlyproducts.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jewerlyproducts.data.materials.MaterialsDao
import com.example.jewerlyproducts.data.materials.MaterialsEntity
import com.example.jewerlyproducts.data.products.ProductsDao
import com.example.jewerlyproducts.data.products.ProductsEntity

@Database(entities = [MaterialsEntity::class, ProductsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract val materialsDao:MaterialsDao
    abstract val productsDao:ProductsDao
}