package com.sanadedu.parent.core.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sanadedu.parent.auth.boarding.ui.boarding.BoardingScreen
import com.sanadedu.parent.auth.domain.navigation.addAuthGraph
import com.sanadedu.parent.client.data.ClientStatus
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.data.Screen
import com.sanadedu.parent.core.presentation.ui.MainScreen
import com.sanadedu.parent.core.presentation.ui.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(
                navigateToBoarding = {
                    navController.navigate(Screen.OnBoardingScreen.route){
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToAuth = {
                    navController.navigate(Screen.AuthScreen.SignInScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToHome = {
                    navController.navigate(Screen.MainScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.OnBoardingScreen.route) {
            BoardingScreen(
                showNext = {
                    LocalClient.getClientViewModel().updateClientStatus(ClientStatus.REGISTERED)
                    navController.navigate(Screen.AuthScreen.SignInScreen.route)
                }
            )
        }

        addAuthGraph(navController)

        composable(route = Screen.MainScreen.route) {
            MainScreen(
                restartTheApp = {
                    navController.navigate(Screen.AuthScreen.SignInScreen.route) {
                        popUpTo(Screen.MainScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}