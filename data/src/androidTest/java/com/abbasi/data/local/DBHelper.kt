package com.abbasi.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider

object DBHelper {

    fun getInMemoryDb() = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        BasicRestaurantDatabase::class.java
    ).allowMainThreadQueries()
        .build()
}
