package com.diegocunha.baseapplication.view.templates

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.diegocunha.baseapplication.core.resource.LoadingType
import com.diegocunha.baseapplication.core.resource.Resource
import com.diegocunha.baseapplication.coroutines.DispatchersProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

abstract class ResourceViewModel<T>(dispatchersProvider: DispatchersProvider) :
    CoroutineViewModel(dispatchersProvider), ResourceFetcher<T> {
    protected open val _resourceLiveData: MutableSharedFlow<Resource<T>> by lazy {
        MutableSharedFlow<Resource<T>>().apply {
            viewModelScope.launch(dispatchersProvider.io) {
                emitAll(loadContent(LoadingType.REPLACE))
            }
        }
    }

    override val resourceLiveData: LiveData<Resource<T>> by lazy { _resourceLiveData.asLiveData() }

    override fun tryAgain() {
        forceLoad()
    }

    open fun refresh() {
        forceLoad(LoadingType.REFRESH)
    }

    open fun forceLoad(loadingType: LoadingType = LoadingType.REPLACE) {
        launchMain {
            _resourceLiveData.emitAll(loadContent(loadingType))
        }
    }

    protected abstract fun loadContent(loadingType: LoadingType): Flow<Resource<T>>

}