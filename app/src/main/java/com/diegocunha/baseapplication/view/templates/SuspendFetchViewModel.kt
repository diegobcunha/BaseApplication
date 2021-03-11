package com.diegocunha.baseapplication.view.templates

import com.diegocunha.baseapplication.core.resource.LoadingType
import com.diegocunha.baseapplication.core.resource.Resource
import com.diegocunha.baseapplication.core.resource.error
import com.diegocunha.baseapplication.core.resource.loading
import com.diegocunha.baseapplication.coroutines.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * A ViewModel to load data as an one-shot request style
 */
abstract class SuspendFetchViewModel<I, O>(dispatchersProvider: DispatchersProvider) :
    FetchViewModel<I, O>(dispatchersProvider) {


    override fun loadContent(loadingType: LoadingType): Flow<Resource<O>> {
        return createFlowData(loadingType)
    }

    private fun createFlowData(loadingType: LoadingType) = flow<Resource<O>> {
        emit(loading(loadingType))
        tryFetch(loadingType)
    }

    private suspend fun FlowCollector<Resource<O>>.tryFetch(loadingType: LoadingType) {
        try {
            val fetchResource = withContextFetch(loadingType)
            when {
                !fetchResource.isSuccess() -> {
                    emit(error(fetchResource.getThrowableOrNull()))
                }

                fetchResource.isNotNullSuccess() -> {
                    emit(withContextToView(loadingType, fetchResource))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e))
        }
    }


    private suspend fun withContextFetch(loadingType: LoadingType) = withContext(fetchContext()) {
        fetch(loadingType).onSuccess {
            lastRequestedValue = it
        }
    }

    private suspend fun withContextToView(
        loadingType: LoadingType,
        fetchResource: Resource<I>
    ): Resource<O> {
        return withContext(toViewContext()) {
            fetchResource.map {
                toView(loadingType, it)
            }
        }
    }


    protected abstract suspend fun fetch(loadingType: LoadingType): Resource<I>
}