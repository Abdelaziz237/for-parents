package com.sanadedu.parent.profile.presentation.info

sealed class ProfileEvent {
    data object StartLoading: ProfileEvent()
    data object GetProfileInfo: ProfileEvent()
    data object Logout: ProfileEvent()
    data object DeleteAccount: ProfileEvent()
    data object StopLoading: ProfileEvent()
}