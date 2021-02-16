package com.abbasi.data.local.daos

import androidx.room.*
import com.abbasi.data.local.models.CategoryEntity
import com.abbasi.data.local.models.CategoryWithProducts
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryEntity")
    fun getAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM CategoryEntity WHERE id = :id")
    fun get(id: Int): Flow<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity")
    fun getAllCategoriesWithProducts(): Flow<List<CategoryWithProducts>>

    @Query("SELECT * FROM CategoryEntity WHERE id = :id")
    fun getWithProducts(id: String): Flow<CategoryWithProducts>

    @Transaction
    @Query("SELECT * FROM ProductEntity, CategoryEntity WHERE ProductEntity.name LIKE :query")
    fun getFilteredProducts(query: String): Flow<List<CategoryWithProducts>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity)

    @Query("DELETE FROM CategoryEntity")
    suspend fun deleteAll()

}