package com.sanadedu.parent.core.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.sanadedu.parent.client.presentation.ClientViewModel
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.domain.navigation.AppNavigation
import com.sanadedu.parent.core.presentation.components.LoadBackgroundPattern
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.CustomToastUI
import com.sanadedu.parent.core.presentation.in_app_messaging.toast.ToastViewModel
import com.sanadedu.parent.feed.data.ToastManager
import com.sanadedu.parent.theme.ParentAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val toastViewModel: ToastViewModel = viewModel()
            ToastManager.initializeToastManager(toastViewModel)

            LocalClient.initialize(
                clientViewModel = viewModel(
                    modelClass = ClientViewModel::class.java,
                    factory = ClientViewModel
                        .ClientViewModelFactory(
                            LocalContext.current
                        )
                )
            )

            Box(modifier = Modifier.fillMaxSize()) {
                LoadBackgroundPattern()
                AppNavigation(navController = navController)
                CustomToastUI(
                    modifier = Modifier
                        .padding(top = 56.dp)
                        .alpha(0.9f)
                        .align(Alignment.TopCenter),
                    viewModel = toastViewModel
                )
            }
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }
}