package com.sanadedu.parent.auth.boarding.ui.boarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanad.studentsapp.core.presentation.boarding.info.BoardingEvent
import com.sanadedu.parent.R
import com.sanadedu.parent.auth.boarding.ui.BodyText
import com.sanadedu.parent.auth.boarding.ui.HeaderText
import com.sanadedu.parent.auth.boarding.ui.ProgressIndicator
import com.sanadedu.parent.auth.boarding.ui.PropertyImage
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.bl

@Composable
fun BoardingScreen(
    viewModel: BoardingViewModel = viewModel(),
    showNext: () -> Unit
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = (88).dp, y = (-109).dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.bg_assets),
            contentDescription = stringResource(id = R.string.boarding_background_image)
        )
        PropertyImage(
            modifier = Modifier
                .padding(top = 40.dp)
                .size(width = 250.dp, height = 250.dp)
                .align(Alignment.TopEnd),
            property = state.image
        )

        Column(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Center
        ) {
            HeaderText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                header = state.header
            )
            Spacer(modifier = Modifier.height(16.dp))
            BodyText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                body = state.body
            )

        }

        Box (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .size(48.dp),
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                onClick = {
                    viewModel.onEvent(
                        BoardingEvent.ShowNext,
                        callback = {
                            showNext()
                        }
                    )
                }
            ) {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    DeepBlue,
                                    bl
                                ),
                            ),
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Inside,
                    painter = painterResource(id = R.drawable.ic_arrow_white),
                    contentDescription = stringResource(id = R.string.go_forward)
                )
            }

            ProgressIndicator(
                modifier = Modifier
                    .size(64.dp),
                state.progress
            )
        }
    }
}