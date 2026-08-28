package com.sanadedu.parent.profile.domain.usecases

import com.sanadedu.parent.profile.domain.repository.ProfileRepository

data class ProfileUseCases(
    val getProfileInfo: GetProfileInfo,
    val deleteAccount: DeleteAccount
) {
    constructor(repo: ProfileRepository) : this(
        getProfileInfo = GetProfileInfo(repo),
        deleteAccount = DeleteAccount(repo)
    )
}
