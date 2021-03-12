package com.diegocunha.baseapplication.view.templates

import com.diegocunha.baseapplication.core.resource.LoadingType
import com.diegocunha.baseapplication.core.resource.Resource
import com.diegocunha.baseapplication.coroutines.DispatchersProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class ReactiveFetchViewModel<I, O>(dispatchersProvider: DispatchersProvider) :
    FetchViewModel<I, O>(dispatchersProvider) {

    private var currentJob: Job? = null

    override fun loadContent(loadingType: LoadingType): Flow<Resource<O>> {
        val flow = MutableSharedFlow<Resource<O>>()
        currentJob?.cancel()
        currentJob = launch {
            fetch(loadingType)
                .flowOn(fetchContext())
                .map { toView(loadingType, it) }
                .flowOn(toViewContext())
        }

        return flow
    }

    abstract fun fetch(loadingType: LoadingType): Flow<I>
}