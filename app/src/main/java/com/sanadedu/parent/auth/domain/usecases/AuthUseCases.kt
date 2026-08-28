package com.sanadedu.parent.auth.domain.usecases

import com.sanadedu.parent.auth.domain.repository.AuthRepository

data class AuthUseCases (
    val verifyEmail: VerifyEmail,
    val verifyOtp: VerifyOtp,
    val resendOtp: ResendOtp,
    val register: Register,
    val signIn: SignIn,
    val signOut: SignOut,
    val forgotPassword: ForgotPassword
) {
    constructor(repo: AuthRepository): this(
        verifyEmail = VerifyEmail(repo),
        verifyOtp = VerifyOtp(repo),
        resendOtp = ResendOtp(repo),
        register = Register(repo),
        signIn = SignIn(repo),
        forgotPassword = ForgotPassword(repo),
        signOut = SignOut()
    )
}