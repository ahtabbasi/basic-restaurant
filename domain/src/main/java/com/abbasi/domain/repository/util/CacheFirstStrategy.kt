package com.abbasi.domain.repository.util

import com.abbasi.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * This implementation of [CachedDataAccessStrategy] emits an empty [Resource.Loading] first
 *
 * then it emits a [Resource.Loading] with cached data received from the passed parameter
 *
 * then it fetches data from remote, if successful that data is stored in cache, if unsuccessful,
 * it emits a [Resource.Invalid] object
 *
 * At the end, the state flow emits all from the cache
 */
object CacheFirstStrategy : CachedDataAccessStrategy {

    override suspend fun <T> performGetOperation(
        getFromCache: (suspend () -> StateFlow<T>),
        getFromRemote: (() -> Flow<Resource<T>>),
        updateCache: (suspend (T) -> Unit)
    ): StateFlow<Resource<T>> {
        TODO("Not yet implemented")
    }
}