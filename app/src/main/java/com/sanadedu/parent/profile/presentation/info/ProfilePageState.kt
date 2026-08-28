package com.sanadedu.parent.profile.presentation.info

data class ProfilePageState(
    val isLoading: Boolean = true,
    val showTryAgain: Boolean = false,
    val parentCode: String = "",
    val username: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val studentsCount: Int = 0,
)
