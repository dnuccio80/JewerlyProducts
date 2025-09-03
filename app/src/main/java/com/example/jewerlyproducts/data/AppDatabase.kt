package com.example.jewerlyproducts.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jewerlyproducts.data.materials.MaterialsDao
import com.example.jewerlyproducts.data.materials.MaterialsEntity

@Database(entities = [MaterialsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract val materialsDao:MaterialsDao
}