package com.abbasi.data.di

import android.content.Context
import com.abbasi.data.local.BasicRestaurantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): BasicRestaurantDatabase {
        return BasicRestaurantDatabase.getDatabase(appContext)
    }

}