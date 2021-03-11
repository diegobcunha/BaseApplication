package com.diegocunha.baseapplication.core.json

import com.squareup.moshi.Moshi

object MoshiManager {
    val moshi = Moshi.Builder().build()
}