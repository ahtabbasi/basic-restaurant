package com.abbasi.data.di

import com.abbasi.data.local.BasicRestaurantDatabase
import com.abbasi.data.local.daos.CategoryDao
import com.abbasi.data.local.daos.ProductDao
import com.abbasi.data.local.datasources.CategoryPersistenceDataSourceImpl
import com.abbasi.data.local.datasources.ProductPersistenceDataSourceImpl
import com.abbasi.domain.datasources.local.CategoryPersistenceDataSource
import com.abbasi.domain.datasources.local.ProductPersistenceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object PersistenceDataSourceModule {

    @Provides
    @ActivityRetainedScoped
    fun provideCategoryDao(db: BasicRestaurantDatabase): CategoryDao {
        return db.categoryDao()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideProductDao(db: BasicRestaurantDatabase): ProductDao {
        return db.productDao()
    }

    @Provides
    @ActivityRetainedScoped
    fun provideCategoryPersistenceDataSource(categoryDao: CategoryDao): CategoryPersistenceDataSource {
        return CategoryPersistenceDataSourceImpl(categoryDao)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideProductPersistenceDataSource(productDao: ProductDao): ProductPersistenceDataSource {
        return ProductPersistenceDataSourceImpl(productDao)
    }

}