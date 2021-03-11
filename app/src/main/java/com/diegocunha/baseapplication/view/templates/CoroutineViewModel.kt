package com.diegocunha.baseapplication.view.templates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.diegocunha.baseapplication.core.resource.Resource
import com.diegocunha.baseapplication.core.resource.loading
import com.diegocunha.baseapplication.coroutines.DispatchersProvider
import com.diegocunha.baseapplication.coroutines.ScopedContextDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class CoroutineViewModel(protected val dispatchersProvider: DispatchersProvider) :
    ViewModel() {

    protected val scopedContextProvider =
        ScopedContextDispatchers(viewModelScope, dispatchersProvider)

    fun io() = scopedContextProvider.io
    fun main() = scopedContextProvider.main
    fun computing() = scopedContextProvider.computing

    fun launchIO(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = launch(io(), start, block)

    fun launchMain(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = launch(main(), start, block)

    fun launchComputing(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = launch(computing(), start, block)

    fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context, start, block)
}

fun <T> CoroutineViewModel.createFetchLiveData(
    fetchBlock: suspend () -> Resource<T>
) = liveData(main()) {
    emit(loading())
    emit(fetchResource(fetchBlock))
}

private suspend fun <T> CoroutineViewModel.fetchResource(
    fetchBlock: suspend () -> Resource<T>
): Resource<T> {
    return try {
        val resource = withContext(io()) { fetchBlock() }
        withContext(main()) { resource }
    } catch (t: Throwable) {
        Resource.error(t)
    }
}