package com.sanadedu.parent.core.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.client.data.ClientStatus
import com.sanadedu.parent.client.presentation.LocalClient

val fontFamily = FontFamily(
    Font(R.font.cairo_regular, FontWeight.Normal),
    Font(R.font.cairo_light, FontWeight.Light),
    Font(R.font.cairo_extra_light, FontWeight.ExtraLight),
    Font(R.font.cairo_medium, FontWeight.Medium),
    Font(R.font.cairo_bold, FontWeight.Bold),
    Font(R.font.cairo_semi_bold, FontWeight.SemiBold),
    Font(R.font.cairo_extra_bold, FontWeight.ExtraBold),
    Font(R.font.cairo_black, FontWeight.Black),
)

@Composable
fun SplashScreen(
    navigateToBoarding: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val credentials = LocalClient.getClientViewModel().userCredentialsState.collectAsState().value

        LaunchedEffect(key1 = credentials) {
            Log.e("client", credentials.clientStatus)
            when (credentials.clientStatus) {
                ClientStatus.NEW_USER.name -> {
                    navigateToBoarding()
                }
                ClientStatus.REGISTERED.name -> {
                    navigateToAuth()
                }
                ClientStatus.NEEDS_REGISTRATION.name -> {
                    navigateToAuth()
                }
                ClientStatus.LOGGED.name -> {
                    navigateToHome()
                }
            }
        }

        // Add this below the first Box for the background gradient
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.72f)
                .offset(y = (-72).dp)
                .alpha(0.5f), // Adjust alpha as needed
            contentScale = ContentScale.Crop,
            imageVector = ImageVector.vectorResource(id = R.drawable.bg_logo_union),
            contentDescription = stringResource(id = R.string.app_logo_background) // Adjust description if needed
        )

        // App logo
        Image(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.Center),
            imageVector = ImageVector.vectorResource(id = R.drawable.sanad_logo),
            contentDescription = stringResource(id = R.string.app_logo)
        )

        Column (
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header text
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.sanad_edu_app),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 28.sp
            )

            // User text
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(id = R.string.for_parents),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 28.sp
            )
        }
    }
}
