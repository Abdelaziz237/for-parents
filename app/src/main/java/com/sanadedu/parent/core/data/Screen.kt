package com.sanadedu.parent.core.data

import com.sanadedu.parent.core.domain.navigation.Routes.ADDRESS_INFO_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.AUTH_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.BIRTH_DATE_INFO_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.COURSE_SCREEN_ARGS
import com.sanadedu.parent.core.domain.navigation.Routes.DETAIL_SIGN_UP_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.FORGOT_PASSWORD_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.HOME_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.MAIN_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.NOTIFICATIONS_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.ON_BOARDING_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.ON_BOARDING_SCREEN_1
import com.sanadedu.parent.core.domain.navigation.Routes.ON_BOARDING_SCREEN_2
import com.sanadedu.parent.core.domain.navigation.Routes.ON_BOARDING_SCREEN_3
import com.sanadedu.parent.core.domain.navigation.Routes.OTP_VERIFICATION_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.PHONE_INFO_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.PROFILE_IMAGE_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.PROFILE_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.SIGN_IN_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.SIGN_UP_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.SPLASH_SCREEN
import com.sanadedu.parent.core.domain.navigation.Routes.STUDENT_SCREEN

sealed class Screen(val route: String) {
    object SplashScreen: Screen(route = SPLASH_SCREEN)

    object OnBoardingScreen: Screen(route = ON_BOARDING_SCREEN) {
        object Boarding1: Screen(route = ON_BOARDING_SCREEN_1)
        object Boarding2: Screen(route = ON_BOARDING_SCREEN_2)
        object Boarding3: Screen(route = ON_BOARDING_SCREEN_3)
    }

    object AuthScreen: Screen(route = AUTH_SCREEN) {
        object SignInScreen: Screen(route = SIGN_IN_SCREEN)
        object SignUpScreen: Screen(route = SIGN_UP_SCREEN)
        object OtpVerificationScreen: Screen(route = OTP_VERIFICATION_SCREEN)

        object DetailSignUpScreen: Screen(route = DETAIL_SIGN_UP_SCREEN) {
            object PhoneInfoScreen: Screen(route = PHONE_INFO_SCREEN)
            object BirthDateInfoScreen: Screen(route = BIRTH_DATE_INFO_SCREEN)
            object AddressInfoScreen: Screen(route = ADDRESS_INFO_SCREEN)
            object ProfileImageScreen: Screen(route = PROFILE_IMAGE_SCREEN)
        }
        object ForgotPasswordScreen: Screen(route = FORGOT_PASSWORD_SCREEN)
    }

    object MainScreen: Screen(route = MAIN_SCREEN) {
        object HomeScreen: Screen(route = HOME_SCREEN)
        object ProfileScreen: Screen(route = PROFILE_SCREEN)
        object NotificationsScreen: Screen(route = NOTIFICATIONS_SCREEN)
        object StudentScreen: Screen(route = STUDENT_SCREEN)
        object CourseScreen: Screen(route = COURSE_SCREEN_ARGS)
    }
}