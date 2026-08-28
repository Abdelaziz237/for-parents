package com.sanadedu.parent.auth.presentation.sign_up.info

data class SignUpPageState(
    val fullname: String,
    val email:String,
    val password: String,
    val emailCampaigns: Boolean
) {
    constructor(): this(
        fullname = "",
        email = "",
        password = "",
        emailCampaigns = false
    )
}