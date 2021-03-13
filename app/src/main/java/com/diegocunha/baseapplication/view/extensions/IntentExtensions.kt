package com.diegocunha.baseapplication.view.extensions

import android.content.Intent
import com.diegocunha.baseapplication.core.exception.InvalidComponentInitializationException

fun Intent.getIntOrThrow(key: String): Int {
    return if(hasExtra(key)) {
        getIntExtra(key, 0)
    } else {
        throw InvalidComponentInitializationException(key)
    }
}