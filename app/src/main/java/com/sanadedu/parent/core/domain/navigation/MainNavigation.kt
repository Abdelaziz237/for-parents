package com.sanadedu.parent.core.domain.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sanadedu.parent.core.data.Screen
import com.sanadedu.parent.feed.presentation.home.HomeScreen
import com.sanadedu.parent.notifications.presentation.NotificationsScreen
import com.sanadedu.parent.profile.presentation.ProfileScreen
import com.sanadedu.parent.student.representation.single_course.CourseScreen
import com.sanadedu.parent.student.representation.student_courses.StudentScreen

@Composable
fun MainGraph(
    mainNavController: NavHostController,
) {
    NavHost(
        navController = mainNavController,
        route = Routes.MAIN_SCREEN,
        startDestination = Screen.MainScreen.HomeScreen.route
    ) {
        composable(route = Screen.MainScreen.HomeScreen.route) {
            HomeScreen(
                onStudentClicked = {
                    mainNavController.navigate(Screen.MainScreen.StudentScreen.route)
                },
//                onProfileSelected = {
//                    mainNavController.navigate(Screen.MainScreen.ProfileScreen.route)
//                }
            )
        }

        composable(route= Screen.MainScreen.StudentScreen.route) {
            StudentScreen(
                onBackPressed = {
                    mainNavController.navigateUp()
                },
                onCourseSelected = {
                    mainNavController.navigate(Screen.MainScreen.CourseScreen.route)
                },
                onNotificationsClicked = {
                    mainNavController.navigate(Screen.MainScreen.NotificationsScreen.route)
                }
            )
        }

        composable(route = Screen.MainScreen.ProfileScreen.route) {
            ProfileScreen(
                onBackPressed = {
                    mainNavController.navigateUp()
                },
                restartTheApp = {
                    mainNavController.navigate(Screen.AuthScreen.SignInScreen.route) {
                        popUpTo(Screen.MainScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.MainScreen.CourseScreen.route) {
            CourseScreen(
                onBackPressed = {
                    mainNavController.navigateUp()
                },
            )
        }

        composable(route = Screen.MainScreen.NotificationsScreen.route) {
            NotificationsScreen(
                onBackPressed = {
                    mainNavController.navigateUp()
                }
            )
        }
    }
}