package com.diegocunha.baseapplication.view.templates

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.diegocunha.baseapplication.core.resource.LoadingType
import com.diegocunha.baseapplication.core.resource.Resource
import com.diegocunha.baseapplication.coroutines.DispatchersProvider
import com.diegocunha.baseapplication.coroutines.fetchValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class ResourceViewModel<T>(dispatchersProvider: DispatchersProvider) :
    CoroutineViewModel(dispatchersProvider), ResourceFetcher<T> {
    protected open val _resourceLiveData: MutableSharedFlow<Resource<T>> by lazy {
        MutableSharedFlow<Resource<T>>().apply {
            fetchValue(loadContent(LoadingType.REPLACE))
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
            _resourceLiveData.fetchValue(loadContent(loadingType))
        }
    }

    protected abstract fun loadContent(loadingType: LoadingType): Flow<Resource<T>>

}