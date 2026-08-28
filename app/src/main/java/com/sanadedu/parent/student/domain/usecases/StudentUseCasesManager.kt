package com.sanadedu.parent.student.domain.usecases

import com.sanadedu.parent.client.data.UserCredentials
import com.sanadedu.parent.student.data.repository.StudentRepositoryImpl


object StudentUseCasesManager {
    private lateinit var studentUseCase: StudentUseCases

    fun initialize(userCredential: UserCredentials) {
        studentUseCase = StudentUseCases(StudentRepositoryImpl(authToken = userCredential.authToken))
    }

    fun getStudentUseCase(): StudentUseCases {
        if (!StudentUseCasesManager::studentUseCase.isInitialized) {
            throw IllegalStateException("StudentUseCasesManager is not initialized. Call initialize() first.")
        }
        return studentUseCase
    }
}
