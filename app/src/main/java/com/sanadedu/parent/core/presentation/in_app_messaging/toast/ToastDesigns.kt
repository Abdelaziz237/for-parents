package com.sanadedu.parent.core.presentation.in_app_messaging.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.ErrorRed
import com.sanadedu.parent.theme.MidNightBlueLight
import com.sanadedu.parent.theme.SuccessGreen
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun CustomToastUI(
    modifier: Modifier = Modifier,
    viewModel: ToastViewModel,
) {
    val state = viewModel.state.value
    AnimatedVisibility(
        modifier = modifier,
        visible = state.isShown && state.type != null,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300))
    ) {
        val (color, icon) = Pair(state.background, state.icon)
        Row(
            modifier = Modifier
                .height(48.dp)
                .background(color = color, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.padding(vertical = 14.dp),
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = stringResource(id = R.string.check_circle)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = state.message),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.width(14.dp))
            Box(
                modifier = Modifier.padding(vertical = 14.dp)
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(color = Color.White)
                    .alpha(0.3f)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Image(
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .clickable(
                        onClick = {
                            viewModel.onEvent(ToastEvent.DismissToast)
                        }
                    ),
                imageVector = ImageVector.vectorResource(R.drawable.ic_close_white),
                contentDescription = stringResource(id = R.string.check_circle)
            )
        }
    }
}
