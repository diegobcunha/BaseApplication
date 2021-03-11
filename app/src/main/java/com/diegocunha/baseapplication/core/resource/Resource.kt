package com.diegocunha.baseapplication.core.resource

/**
 * A class to wrap the data and provide status and help to handling errors using LiveData
 * @param <T> the type of resource
 * @param <E> throwable triggered from some data source or interactor
 */
@Suppress("unused")
sealed class Resource<T>(open val data: T?) {

    companion object {

        fun <T> success(data: T? = null) = Success<T>(data)

        fun <T> error(throwable: Throwable? = null, data: T? = null) = Error<T>(throwable)

        fun <T> loading(type: LoadingType? = null, data: T? = null) = Loading<T>(type, data)
    }

    fun getOrNull() = data

    fun getOrDefault(default: T) = data ?: default

    fun getThrowableOrNull() = (this as? Error)?.throwable

    fun isLoading() = this is Loading

    fun isSuccess() = this is Success

    fun isError() = this is Error

    fun isNotNullSuccess() = this is Success && data != null

    inline fun <R> mapNotNull(mapBlock: (T) -> R?): Resource<R> {
        return map {
            if (it != null) {
                mapBlock(it)
            } else null
        }
    }

    inline fun <R> map(mapBlock: (T?) -> R?): Resource<R> {
        return when (this) {
            is Error -> error(throwable)
            is Loading -> loading()
            is Success -> {
                val r = mapBlock(data)
                success(r)
            }
        }
    }

    inline fun <R> mapResource(mapBlock: (T?) -> Resource<R>): Resource<R> {
        return when (this) {
            is Error -> error(throwable)
            is Loading -> loading()
            is Success -> {
                mapBlock(data)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> cast(): Resource<R> {
        return map { it as? R? }
    }

    inline fun onLoading(block: (LoadingType?) -> Unit): Resource<T> {
        if (this is Loading) {
            block(type)
        }
        return this
    }

    inline fun onSuccess(block: (T?) -> Unit): Resource<T> {
        if (isSuccess()) {
            block(getOrNull())
        }
        return this
    }

    inline fun onError(block: (Throwable?) -> Unit): Resource<T> {
        if (isError()) {
            block(getThrowableOrNull())
        }
        return this
    }

    data class Success<T>(override val data: T?) : Resource<T>(data)
    data class Error<T>(val throwable: Throwable?, override val data: T? = null) : Resource<T>(data) {
        fun <R> error() = error<R>(getThrowableOrNull())
    }
    data class Loading<T>(val type: LoadingType?, override val data: T? = null) : Resource<T>(data)
}

fun <T> success(value: T?): Resource<T> = Resource.success(value)
fun <T> error(throwable: Throwable?, data: T? = null): Resource<T> = Resource.error(throwable, data)
fun <T> loading(type: LoadingType? = null, data: T? = null): Resource<T> = Resource.loading(type, data)

/**
 * Indicates the type of loading used in Resource.Loading
 */
enum class LoadingType {
    /**
     * Used when the current content shown on view will be replaced to a new one, usually removing
     * the old one
     */
    REPLACE,

    /**
     * Used when the current content shown on view will be refreshed
     */
    REFRESH,

    /**
     * Used when more content will be shown additionally with the current content
     */
    PAGINATION
}