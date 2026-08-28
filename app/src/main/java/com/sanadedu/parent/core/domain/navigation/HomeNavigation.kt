package com.sanadedu.parent.core.domain.navigation

//fun NavGraphBuilder.addMainGraph(mainNavController: NavController) {
//    navigation(startDestination = Screen.MainScreen.HomeScreen.route, route = MAIN_SCREEN) {
//        composable(route = Screen.MainScreen.HomeScreen.route) {
//            HomeScreen(
//                onBackPressed = {
//                    mainNavController.popBackStack()
//                },
//                onStudentSelected = {
//                    mainNavController.navigate(Screen.MainScreen.StudentScreen.route)
//                },
//                onProfileSelected = {
//                    mainNavController.navigate(Screen.MainScreen.ProfileScreen.route)
//                },
//                onNotificationsSelected = {
//                    mainNavController.navigate(Screen.MainScreen.NotificationsScreen.route)
//                }
//            )
//        }
//
//        composable(route= Screen.MainScreen.StudentScreen.route) {
//            StudentScreen(
//                onBackPressed = {
//                    mainNavController.popBackStack()
//                },
//                onCourseSelected = { courseID ->
//                    val route = StringBuilder(Routes.COURSE_SCREEN).append('/').append(courseID).toString()
//                    mainNavController.navigate(route)
//                },
//            )
//        }
//
//        composable(route = Screen.MainScreen.ProfileScreen.route) {
//            ProfileScreen(
//                onBackPressed = {
//                    mainNavController.popBackStack()
//                }
//            )
//        }
//
//        composable(route = Screen.MainScreen.CourseScreen.route) { backStackEntry ->
//            CourseScreen(
//                onBackPressed = {
//                    mainNavController.popBackStack()
//                },
//                courseID = backStackEntry.arguments?.getString("courseID"
//                ) ?: TODO("NOT PASSED VALUE"))
//        }
//
//        composable(route = Screen.MainScreen.NotificationsScreen.route) {
//            NotificationsScreen(
//                onBackPressed = {
//                    mainNavController.popBackStack()
//                }
//            )
//        }
//    }
//}
