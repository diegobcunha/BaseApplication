package com.diegocunha.baseapplication.view.templates.viewmodel

import com.diegocunha.baseapplication.core.resource.LoadingType
import com.diegocunha.baseapplication.coroutines.DispatchersProvider

abstract class FetchViewModel<I, O>(dispatchersProvider: DispatchersProvider) :
    ResourceViewModel<O>(dispatchersProvider) {

    protected open var lastRequestedValue: I? = null

    protected open fun fetchContext() = dispatchersProvider.io

    protected open fun liveDataContext() = main()

    protected open fun toViewContext() = dispatchersProvider.computing

    protected abstract suspend fun toView(loadingType: LoadingType, model: I?): O?
}