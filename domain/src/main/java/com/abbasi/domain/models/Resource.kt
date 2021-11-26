package com.abbasi.domain.models

import com.abbasi.domain.models.Resource.Valid
import com.abbasi.domain.models.Resource.Invalid
import com.abbasi.domain.models.Resource.Loading

/**
 * A generic data wrapper with 3 possible states : [Valid], [Invalid], [Loading]
 */
sealed class Resource<out T> {
    data class Valid<T>(val data: T) : Resource<T>()
    data class Invalid<T>(val message: String, val data: T? = null) : Resource<T>()
    data class Loading<T>(val data: T? = null) : Resource<T>()
}

/**
 * Performs the [transform] action on data and returns a new [Resource] with the same state but new data
 */
fun <T, R> Resource<T>.transform(
    transform: ((value: T) -> R)
): Resource<R> = when (this) {
    is Valid -> Valid(transform.invoke(data))
    is Invalid -> Invalid(message, data?.let { transform.invoke(it) })
    is Loading -> Loading(data?.let { transform.invoke(it) })
}


fun <T> Resource<T>.getDataOrNull() = when (this) {
    is Valid -> data
    is Invalid -> data
    is Loading -> data
}