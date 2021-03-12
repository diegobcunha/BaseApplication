package com.diegocunha.baseapplication.view.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Manage the binding of fragments
 */
interface ViewBindingHolder<T : ViewBinding> {

    /**
     * Binding of the fragment
     */

    val binding: T?

    fun initBinding(binding: T, fragment: Fragment, onBound: (T.() -> Unit)? = null): View

    fun requireBinding(block: (T.() -> Unit)? = null): T

}