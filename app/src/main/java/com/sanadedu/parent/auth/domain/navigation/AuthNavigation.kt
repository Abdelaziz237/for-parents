package com.sanadedu.parent.auth.domain.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sanadedu.parent.auth.presentation.otp_verification.OtpVerificationScreen
import com.sanadedu.parent.auth.presentation.reset_password.ForgotPasswordScreen
import com.sanadedu.parent.auth.presentation.sign_in.SignInScreen
import com.sanadedu.parent.auth.presentation.sign_up.SignUpScreen
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.DetailSignUpScreen
import com.sanadedu.parent.core.data.Screen

fun NavGraphBuilder.addAuthGraph(navController: NavHostController) {
    var email = "error"
    navigation(startDestination = Screen.AuthScreen.SignInScreen.route, route = Screen.AuthScreen.route) {
        composable(route = Screen.AuthScreen.SignInScreen.route) {
            SignInScreen (
                onSignedIn = {
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                },
                toOtpVerification = {
                    navController.navigate(Screen.AuthScreen.OtpVerificationScreen.route)
                },
                toCompleteRegistration = { input ->
                    email = input
                    navController.navigate(Screen.AuthScreen.DetailSignUpScreen.route)
                },
                onNavigateUp = {
                    navController.navigateUp()
                },
                createNewAccount = {
                    navController.navigate(Screen.AuthScreen.SignUpScreen.route)
                },
                forgotPassword = {
                    navController.navigate(Screen.AuthScreen.ForgotPasswordScreen.route)
                }
            )
        }
        composable(route = Screen.AuthScreen.SignUpScreen.route) {
            SignUpScreen (
                toCompleteRegistration = { input ->
                    email = input
                    navController.navigate(Screen.AuthScreen.DetailSignUpScreen.route)
                },
                toOtpVerification = { input ->
                    email = input
                    navController.navigate(Screen.AuthScreen.OtpVerificationScreen.route)
                },
                onNavigateUp = {
                    navController.navigateUp()
                },
                hasAccount = {
                    navController.navigate(Screen.AuthScreen.SignInScreen.route)
                }
            )
        }

        composable(
            route = Screen.AuthScreen.OtpVerificationScreen.route,
        ) {
            OtpVerificationScreen(
                onNavigateUp = {
                    navController.navigateUp()
                },
                email = email,
                onVerified = {
                    navController.navigate(Screen.AuthScreen.DetailSignUpScreen.route)
                }
            )
        }

        composable(
            route = Screen.AuthScreen.DetailSignUpScreen.route,
        ) {
            DetailSignUpScreen(
                email = email,
                onNavigateUp = {
                    navController.navigateUp()
                },
                onSubmit = {
                    navController.navigate(Screen.AuthScreen.SignInScreen.route)
                }
            )
        }
        composable(route = Screen.AuthScreen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen (
                onNavigateUp = {
                    navController.navigateUp()
                },
                onPasswordReset = {
                    navController.navigate(Screen.AuthScreen.SignInScreen.route)
                }
            )
        }
    }
}