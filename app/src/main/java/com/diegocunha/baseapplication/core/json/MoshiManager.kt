package com.diegocunha.baseapplication.core.json

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiManager {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
}