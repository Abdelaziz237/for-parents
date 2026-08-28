package com.sanadedu.parent.core.domain.usecases.response_validator

import com.sanadedu.parent.core.data.NetworkFailedCodes.BAD_REQUEST
import com.sanadedu.parent.core.data.NetworkFailedCodes.EMAIL_EXISTS
import com.sanadedu.parent.core.data.NetworkFailedCodes.NOT_FOUND
import com.sanadedu.parent.core.data.ValidationCodes.ACCESS_DENIED
import com.sanadedu.parent.core.data.ValidationCodes.BAD_NETWORK
import com.sanadedu.parent.core.data.ValidationCodes.EMAIL_ALREADY_EXISTS
import com.sanadedu.parent.core.data.ValidationCodes.EMAIL_NOT_FOUND
import com.sanadedu.parent.core.data.ValidationCodes.PARSING_EXCEPTION
import com.sanadedu.parent.core.data.ValidationCodes.UNAUTHORIZED
import com.sanadedu.parent.core.data.ValidationCodes.UNDEFINED
import com.sanadedu.parent.core.data.ValidationCodes.WRONG_PASSWORD
import com.sanadedu.parent.core.domain.model.ApiResponse
import com.sanadedu.parent.core.domain.model.ConversionResult
import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.utils.decodeJsonFromString


class DataValidator<T>(
    private val dtoClass: Class<T>
) {
    fun validateData(response: ApiResponse, needsDecoding: Boolean = true): ValidationStatus<T> {
        val result: ConversionResult<T>
        return when(response) {
            is ApiResponse.Success -> {
                result = if (needsDecoding) decodeJsonFromString(response.data, dtoClass)
                else ConversionResult.Success("" as T)
                when (result) {
                    is ConversionResult.Success -> {
                        ValidationStatus.Valid(result.data)
                    }
                    is ConversionResult.Error -> {
                        ValidationStatus.NotValid(PARSING_EXCEPTION)
                    }
                }

            }
            is ApiResponse.Unauthorized -> {
                ValidationStatus.NotValid(UNAUTHORIZED)
            }
            is ApiResponse.Refused -> {
                ValidationStatus.NotValid(ACCESS_DENIED)
            }
            is ApiResponse.Failed -> {
                when(response.cause) {
                    BAD_REQUEST -> {
                        ValidationStatus.NotValid(WRONG_PASSWORD)
                    }
                    EMAIL_EXISTS -> {
                        ValidationStatus.NotValid(EMAIL_ALREADY_EXISTS)
                    }
                    NOT_FOUND -> {
                        ValidationStatus.NotValid(EMAIL_NOT_FOUND)
                    }
                    else -> {
                        ValidationStatus.NotValid(UNDEFINED)
                    }
                }
            }
            is ApiResponse.Error -> {
                ValidationStatus.NotValid(BAD_NETWORK)
            }
        }
    }
}
