package com.diegocunha.baseapplication.view.extensions

import com.diegocunha.baseapplication.core.resource.LoadingType
import com.diegocunha.baseapplication.core.resource.error
import com.diegocunha.baseapplication.core.resource.loading
import com.diegocunha.baseapplication.core.resource.success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Maps a flow of T in a flow of Resource<T> catching errors and emitting resource error and optionally
 * emitting the loading
 * @param loadingType type of loading to be emitted when started OR null if no loading should be passed
 */
fun <T> Flow<T>.toResourceFlow(loadingType: LoadingType? = null) = map {
    success<T>(it)
}.catch {
    emit(error<T>(it))
}.onStart {
    if (loadingType != null) {
        emit(loading(loadingType))
    }
}