package com.sanadedu.parent.profile.domain.repository

import com.sanadedu.parent.core.domain.model.ApiResponse

interface ProfileRepository {
    suspend fun getProfileInfo(): ApiResponse
    suspend fun deleteAccount(): ApiResponse
}