package com.diegocunha.baseapplication.view.templates.viewmodel

import androidx.lifecycle.LiveData
import com.diegocunha.baseapplication.core.resource.Resource

/**
 * Resource loader of fragment
 */
interface ResourceFetcher<T> {

    /**
     * Flow wrapping the resource content
     */
    val resourceLiveData: LiveData<Resource<T>>

    /**
     * try load again
     */
    fun tryAgain()
}
