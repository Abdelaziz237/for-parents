package com.sanadedu.parent.student.domain.usecases

import com.sanadedu.parent.core.domain.model.ValidationStatus
import com.sanadedu.parent.core.domain.usecases.response_validator.DataValidator
import com.sanadedu.parent.student.data.dto.NotificationsResponse
import com.sanadedu.parent.student.data.dto.SessionsResponse
import com.sanadedu.parent.student.domain.repository.StudentRepository

class GetNotifications(
    private val repository: StudentRepository
) {

    private val validator: DataValidator<NotificationsResponse> = DataValidator(
        dtoClass = NotificationsResponse::class.java
    )

    suspend operator fun invoke(cardId: String): ValidationStatus<NotificationsResponse> {
        val response = repository.getNotifications(cardId = cardId)
        return validator.validateData(response)
    }
}
