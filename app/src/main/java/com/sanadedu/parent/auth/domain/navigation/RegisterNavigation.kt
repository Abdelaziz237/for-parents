package com.sanadedu.parent.auth.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.AddressInfoScreen
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.BirthdateInfoScreen
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.DetailSignUpViewModel
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.PhoneInfoScreen
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.ProfileImageScreen
import com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up.info.RegisterFormEvent
import com.sanadedu.parent.core.data.Screen

@Composable
fun RegisterGraph(
    registerNavController: NavHostController,
    viewModel: DetailSignUpViewModel,
    onSubmit: () -> Unit
) {
    NavHost(
        navController = registerNavController,
        route = Screen.AuthScreen.DetailSignUpScreen.route,
        startDestination = Screen.AuthScreen.DetailSignUpScreen.PhoneInfoScreen.route)
    {
        composable(route = Screen.AuthScreen.DetailSignUpScreen.PhoneInfoScreen.route) {
            PhoneInfoScreen(
                viewModel = viewModel,
                onSubmit = { phoneNumber ->
                    viewModel.onEvent(
                        RegisterFormEvent.SubmitPhoneNumber(phoneNumber),
                        callback = {
                            registerNavController.navigate(Screen.AuthScreen.DetailSignUpScreen.AddressInfoScreen.route)
                        }
                    )
                }
            )
        }
        composable(route = Screen.AuthScreen.DetailSignUpScreen.AddressInfoScreen.route) {
            AddressInfoScreen(
                viewModel = viewModel,
                onSubmit = { gov, city, address ->
                    viewModel.onEvent(
                        RegisterFormEvent.SubmitAddress(
                            government = gov,
                            city = city,
                            address = address
                        ),
                        callback = {
                            registerNavController.navigate(Screen.AuthScreen.DetailSignUpScreen.BirthDateInfoScreen.route)
                        }
                    )
                }
            )
        }
        composable(route = Screen.AuthScreen.DetailSignUpScreen.BirthDateInfoScreen.route) {
            BirthdateInfoScreen(
                viewModel = viewModel,
                onSubmit = { birthdate, gender ->
                    viewModel.onEvent(
                        RegisterFormEvent.SubmitBirthdateAndGender(
                            birthdate = birthdate,
                            gender = gender
                        ),
                        callback = {
                            viewModel.onEvent(
                                RegisterFormEvent.SubmitForm,
                                callback = {
                                    onSubmit()
                                }
                            )
//                            viewModel.onEvent(
//                                RegisterFormEvent.SubmitGender(
//                                    gender = gender
//                                ),
//                                callback = {

                            //TODO: registerNavController.navigate(Screen.AuthScreen.DetailSignUpScreen.ProfileImageScreen.route)

//                                }
//                            )
                        }
                    )
                }
            )
        }
        composable(route = Screen.AuthScreen.DetailSignUpScreen.ProfileImageScreen.route) {
            ProfileImageScreen(
                viewModel = viewModel,
                onSubmit = {
                    viewModel.onEvent(
                        RegisterFormEvent.SubmitForm,
                        callback = {
                            onSubmit()
                        }
                    )
                }
            )
        }
    }
}