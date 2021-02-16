package com.abbasi.data.di

import com.abbasi.data.remote.api.ApiClient
import com.abbasi.data.remote.datasources.CategoryRemoteDataSourceImpl
import com.abbasi.domain.datasources.remote.CategoryRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RemoteDataSourceModule {

    @Provides
    @ActivityRetainedScoped
    fun provideCategoryRemoteDataSource(apiClient: ApiClient): CategoryRemoteDataSource {
        return CategoryRemoteDataSourceImpl(apiClient)
    }

}