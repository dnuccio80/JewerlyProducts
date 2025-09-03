package com.example.jewerlyproducts.di

import android.app.Application
import androidx.room.Room
import com.example.jewerlyproducts.data.AppDatabase
import com.example.jewerlyproducts.data.materials.MaterialsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideAppDataBase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideMaterialsDao(appDatabase: AppDatabase):MaterialsDao {
        return appDatabase.materialsDao
    }

}