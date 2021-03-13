package com.diegocunha.baseapplication.view.templates.viewmodel

import com.diegocunha.baseapplication.core.resource.LoadingType
import com.diegocunha.baseapplication.core.resource.Resource
import com.diegocunha.baseapplication.coroutines.DispatchersProvider
import com.diegocunha.baseapplication.view.extensions.toResourceFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Class responsible to fetch data given an event triggered
 */
abstract class ReactiveFetchViewModel<I, O>(dispatchersProvider: DispatchersProvider) :
    FetchViewModel<I, O>(dispatchersProvider) {

    private var currentJob: Job? = null

    override fun loadContent(loadingType: LoadingType): Flow<Resource<O>> {
        val flow = MutableSharedFlow<Resource<O>>()
        currentJob?.cancel()
        currentJob = launch {
            fetch(loadingType)
                .onEach { lastRequestedValue = it }
                .flowOn(fetchContext())
                .toResourceFlow(loadingType)
                .map { requestedValue ->
                    requestedValue.map { toView(loadingType, it) }
                }
                .flowOn(toViewContext())
                .collect { flow.emit(it) }
        }

        return flow
    }

    abstract fun fetch(loadingType: LoadingType): Flow<I>
}