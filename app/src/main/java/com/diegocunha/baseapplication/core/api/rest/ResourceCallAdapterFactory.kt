package com.diegocunha.baseapplication.core.api.rest

import com.diegocunha.baseapplication.core.resource.Resource
import com.diegocunha.baseapplication.core.resource.ResourceCallAdapter
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Adapter to convert responses to Resource response
 */
class ResourceCallAdapterFactory() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Resource::class.java -> {
                    val result = getParameterUpperBound(0, callType as ParameterizedType)
                    ResourceCallAdapter(result)
                }
                else -> null
            }
        }
        else -> null
    }
}