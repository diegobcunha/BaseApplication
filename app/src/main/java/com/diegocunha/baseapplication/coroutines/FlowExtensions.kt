package com.diegocunha.baseapplication.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun <T> Flow<T>.fetchValue(source: Flow<T>) {
    merge(this, source)
}