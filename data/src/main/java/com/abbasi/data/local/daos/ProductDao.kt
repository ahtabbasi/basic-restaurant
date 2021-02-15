package com.abbasi.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abbasi.data.local.models.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    fun getAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE id = :productId AND categoryId = :categoryId")
    fun get(productId: String, categoryId: String): Flow<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE categoryId = :categoryId")
    fun getByCategoryId(categoryId: String): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(products: List<ProductEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    @Query("DELETE FROM ProductEntity")
    suspend fun deleteAll()
}