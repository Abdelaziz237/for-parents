package com.sanadedu.parent.client.data


data class UserCredentials (
    var authToken: String,
    var clientStatus: String,
    var username: String,
    var profileImage: String
) {
    constructor(): this(
        authToken = "",
        clientStatus = "",
        username = "",
        profileImage = ""
    )
}