package com.sanadedu.parent.auth.domain.util

import com.sanadedu.parent.auth.data.repository.AuthRepositoryImpl
import com.sanadedu.parent.auth.domain.usecases.AuthUseCases


object AuthUseCasesManager {
    private lateinit var authUseCases: AuthUseCases

    private fun initialize() {
        authUseCases = AuthUseCases(AuthRepositoryImpl())
    }

    fun getAuthUseCases(): AuthUseCases {
        if (!AuthUseCasesManager::authUseCases.isInitialized) {
            initialize()
        }
        return authUseCases
    }
}
