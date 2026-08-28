package com.sanadedu.parent.core.presentation.appbars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.components.NotificationButton
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.TextColor


@Composable
fun BasicAppBar(
    text: String,
    hasNotificationIcon: Boolean = false,
    onNotificationsClicked: () -> Unit = {},
    navigateUp: () -> Unit
) {
    Box(modifier = Modifier
        .background(Color.Transparent)
        .fillMaxWidth()
        .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = text,
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 24.sp,
            color = TextColor
        )
        IconButton(
            modifier = Modifier
                .rotate(180f)
                .align(Alignment.CenterStart)
                .background(
                    Color.Transparent,
                    shape = RoundedCornerShape(24.dp)
                ),
            onClick = { navigateUp() }
        ) {
            Image(
                modifier = Modifier.fillMaxSize().background(Color.Transparent),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.back_button)
            )
        }
        if (hasNotificationIcon) {
            NotificationButton(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .align(Alignment.CenterEnd)
                    .clickable {
                        onNotificationsClicked()
                    },
                hasNotifications = false
            )
        }
    }
}

