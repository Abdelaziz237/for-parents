package com.sanadedu.parent.student.data.dto

import com.sanadedu.parent.feed.domain.dto.MetadataDTO

data class NotificationsResponse(
    val status: String,
    val data: List<NotificationDTO>,
    val metadata: MetadataDTO
)

data class NotificationDTO(
    val _id: String,
    val center: String,
    val title: String,
    val description: String,
//    val resources: List<Any>, // Replace Any with the actual type if known
    val createdBy: CreatedByDTO,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val name: String,
    val code: String
)
