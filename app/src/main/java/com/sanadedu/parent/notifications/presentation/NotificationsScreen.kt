package com.sanadedu.parent.notifications.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.appbars.BasicAppBar
import com.sanadedu.parent.core.presentation.components.EmptyState
import com.sanadedu.parent.core.presentation.components.LoadingState
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.MidNightBlue
import com.sanadedu.parent.theme.MidNightBlueLight

@Composable
fun NotificationsScreen(
    onBackPressed: () -> Unit,
    viewModel: NotificationsViewModel = viewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.getNotifications()
    }

    if (state.isLoading) {
        LoadingState()
    } else Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            BasicAppBar(
                navigateUp = onBackPressed,
                text = stringResource(id = R.string.notifications)
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (state.notifications.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    items(state.notifications) { announcement ->
                        AnnouncementItem(announcement = announcement)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnnouncementItem(
    announcement: NotificationItem
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                model = announcement.senderImage,
                contentDescription = stringResource(id = R.string.assistant_image)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = AnnotatedString.Builder().apply {
                        withStyle(style = SpanStyle(color = DeepBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
                            append(announcement.senderName)
                        }
                        append(" " + stringResource(id = R.string.from) + " ")
                        withStyle(style = SpanStyle(color = DeepBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
                            append(announcement.centerName)
                        }
                        append(" " + stringResource(id = R.string.sent_you_a_announcement))
                    }.toAnnotatedString(),
                    fontFamily = fontFamily,
                    color = MidNightBlue,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row((Modifier.height(IntrinsicSize.Max))) {
                    Spacer(
                        modifier = Modifier
                            .width(5.dp)
                            .fillMaxHeight()
                            .background(
                                color = MidNightBlueLight,
                                shape = RoundedCornerShape(50.dp)
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = announcement.title, color = MidNightBlue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = announcement.content, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = announcement.date,
                        fontFamily = fontFamily,
                        color = MidNightBlueLight,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}