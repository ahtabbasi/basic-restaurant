package com.abbasi.data.di

import com.abbasi.domain.datasources.local.CategoryPersistenceDataSource
import com.abbasi.domain.datasources.local.ProductPersistenceDataSource
import com.abbasi.domain.datasources.remote.CategoryRemoteDataSource
import com.abbasi.domain.repository.CategoryRepository
import com.abbasi.domain.repository.CategoryRepositoryImpl
import com.abbasi.domain.repository.ProductRepository
import com.abbasi.domain.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideCategoryRepository(
        categoryRDS: CategoryRemoteDataSource,
        categoryPDS: CategoryPersistenceDataSource,
        productPDS: ProductPersistenceDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryRDS, categoryPDS, productPDS)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideProductRepository(
        productPDS: ProductPersistenceDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(productPDS)
    }

}