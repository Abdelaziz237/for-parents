package com.sanadedu.parent.auth.domain.usecases

import com.sanadedu.parent.client.data.ClientStatus
import com.sanadedu.parent.client.presentation.LocalClient

class SignOut {
    operator fun invoke() {
        LocalClient.getClientViewModel().setUserCredentials(
            token = "",
            username = "",
            profileImage = "",
            callback = { LocalClient.getClientViewModel().updateClientStatus(ClientStatus.REGISTERED) }
        )
    }
}