package com.diegocunha.baseapplication.view.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.diegocunha.baseapplication.core.resource.Resource
import com.diegocunha.baseapplication.core.resource.ResourceObserver


fun <T> LiveData<Resource<T>>.withOwner(lifecycleOwner: LifecycleOwner): ResourceObserver<T> {
    val resourceObserver = ResourceObserver<T>(lifecycleOwner)
    resourceObserver.liveData = this
    return resourceObserver
}