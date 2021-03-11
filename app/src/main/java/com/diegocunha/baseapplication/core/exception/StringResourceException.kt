package com.diegocunha.baseapplication.core.exception

import androidx.annotation.Keep

/**
 * An exception that contains a friendly message to be shown to user
 */
@Keep
open class StringResourceException(
    val friendlyMessage: String?,
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)