package com.sanadedu.parent.core.domain.utils

import com.sanadedu.parent.R
import com.sanadedu.parent.core.data.ValidationCodes.ACCESS_DENIED
import com.sanadedu.parent.core.data.ValidationCodes.BAD_NETWORK
import com.sanadedu.parent.core.data.ValidationCodes.NOT_FOUND
import com.sanadedu.parent.core.data.ValidationCodes.PARSING_EXCEPTION
import com.sanadedu.parent.core.data.ValidationCodes.UNAUTHORIZED
import com.sanadedu.parent.core.data.ValidationCodes.UNDEFINED

fun showToast(cause: Int) {
        when(cause) {
            UNAUTHORIZED -> {
                R.string.unauthorized
            }
            PARSING_EXCEPTION -> {
                R.string.parsing_error
            }
            ACCESS_DENIED -> {
                R.string.access_denied
            }
            NOT_FOUND -> {
                R.string.not_found
            }
            BAD_NETWORK -> {
                R.string.bad_network
            }
            UNDEFINED -> {
                R.string.undefined
            }
        }
    }