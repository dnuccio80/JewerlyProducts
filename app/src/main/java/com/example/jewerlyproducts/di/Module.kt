package com.example.jewerlyproducts.di

import android.app.Application
import androidx.room.Room
import com.example.jewerlyproducts.data.AppDatabase
import com.example.jewerlyproducts.data.expenses.ExpensesDao
import com.example.jewerlyproducts.data.materials.MaterialsDao
import com.example.jewerlyproducts.data.products.ProductsDao
import com.example.jewerlyproducts.data.sells.SellDao
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

    @Provides
    @Singleton
    fun provideProductsDao(appDatabase: AppDatabase):ProductsDao {
        return appDatabase.productsDao
    }

    @Provides
    @Singleton
    fun provideSellDao(appDatabase: AppDatabase):SellDao {
        return appDatabase.sellDao
    }

    @Provides
    @Singleton
    fun provideExpenseDao(appDatabase: AppDatabase):ExpensesDao {
        return appDatabase.expensesDao
    }

}