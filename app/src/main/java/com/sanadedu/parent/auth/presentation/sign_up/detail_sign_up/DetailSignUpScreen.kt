package com.sanadedu.parent.auth.presentation.sign_up.detail_sign_up

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.domain.navigation.RegisterGraph
import com.sanadedu.parent.client.data.ClientStatus
import com.sanadedu.parent.client.presentation.LocalClient
import com.sanadedu.parent.core.presentation.appbars.BasicAppBar

@Composable
fun DetailSignUpScreen(
    email: String,
    registerNavController: NavHostController = rememberNavController(),
    onSubmit: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: DetailSignUpViewModel = viewModel(factory = DetailSignUpViewModelFactory(email))
 ) {
    LaunchedEffect(key1 = Unit) {
        LocalClient.getClientViewModel().updateClientStatus(ClientStatus.NEEDS_REGISTRATION)
    }

    Column {
        BasicAppBar(
            navigateUp = onNavigateUp,
            text = stringResource(id = R.string.new_account)
        )

        Box(modifier = Modifier
            .padding(top = 24.dp)
            .weight(1f)) {
            RegisterGraph(
                registerNavController = registerNavController,
                viewModel = viewModel,
                onSubmit = onSubmit
            )
        }
    }
}