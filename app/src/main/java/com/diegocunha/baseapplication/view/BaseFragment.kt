package com.diegocunha.baseapplication.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.diegocunha.baseapplication.core.resource.LoadingType
import com.diegocunha.baseapplication.view.extensions.withOwner
import com.diegocunha.baseapplication.view.templates.viewmodel.ResourceViewModel

abstract class BaseFragment<T>(@LayoutRes layoutResId: Int = 0) : Fragment(layoutResId) {

    protected abstract val resourceViewModel: ResourceViewModel<T>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resourceViewModel.resourceLiveData
            .withOwner(viewLifecycleOwner)
            .onLoading { onLoading(it) }
            .onSuccess { onSuccess(it) }
            .onError { onError(it) }
            .observe()
    }

    protected fun onLoading(loadingType: LoadingType?) {
        Log.d(BaseFragment::class.java.name, "onLoading")
    }


    protected fun onSuccess(data: T?) {
        Log.d(BaseFragment::class.java.name, "Sucess")
    }

    protected fun onError(throwable: Throwable?) {

        Log.d(BaseFragment::class.java.name, "Error")
    }
}