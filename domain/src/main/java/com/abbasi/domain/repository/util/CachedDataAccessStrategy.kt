package com.abbasi.domain.repository.util

import com.abbasi.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


interface CachedDataAccessStrategy {
    suspend fun <T> performGetOperation(
        getFromCache: (suspend () -> StateFlow<T>),
        getFromRemote: (() -> Flow<Resource<T>>),
        updateCache: (suspend (T) -> Unit)
    ): StateFlow<Resource<T>>
}