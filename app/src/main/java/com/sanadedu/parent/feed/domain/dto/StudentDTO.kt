package com.sanadedu.parent.feed.domain.dto

import com.sanadedu.parent.feed.data.GlobeVars.NULL

data class StudentDTO(
    val grade: GradeDTO = GradeDTO(NULL, NULL, NULL),
    val _id: String,
    val fullname: String,
    val email: String,
    val code: String,
    val hasParent: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val isRegistered: Boolean,
    val isBlocked: Boolean,
    val address: String,
    val birthDate: String,
    val city: String,
    val gender: String,
    val governorate: String,
    val phoneNumber: String,
    val schoolName: String,
//    val schoolType: String
    val card: String?,
    val coursesCount: Int
)
