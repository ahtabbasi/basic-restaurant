package com.abbasi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abbasi.data.local.daos.CategoryDao
import com.abbasi.data.local.daos.ProductDao
import com.abbasi.data.local.models.CategoryEntity
import com.abbasi.data.local.models.ProductEntity

private const val DB_NAME = "characters"

@Database(entities = [CategoryEntity::class, ProductEntity::class], version = 1)
abstract class BasicRestaurantDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var instance: BasicRestaurantDatabase? = null

        fun getDatabase(context: Context): BasicRestaurantDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, BasicRestaurantDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
