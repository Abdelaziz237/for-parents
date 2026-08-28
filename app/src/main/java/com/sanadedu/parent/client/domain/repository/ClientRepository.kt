package com.sanadedu.parent.client.domain.repository

import com.sanadedu.parent.client.data.ClientStatus
import com.sanadedu.parent.client.data.UserCredentials


interface ClientRepository {
    suspend fun getUserPreferences(): UserCredentials
    suspend fun setUserCredentials(token: String, username: String, profileImage: String)
    suspend fun updateClientStatus(status: ClientStatus)
    suspend fun clearUserData()
}