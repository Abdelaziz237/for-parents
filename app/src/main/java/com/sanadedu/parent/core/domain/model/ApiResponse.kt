package com.sanadedu.parent.core.domain.model

sealed class ApiResponse {
    data class Success(val data: String) : ApiResponse()

    object Error : ApiResponse()
    data class Failed(val cause: Int) : ApiResponse()
    object Refused : ApiResponse()
    object Unauthorized : ApiResponse()
}

