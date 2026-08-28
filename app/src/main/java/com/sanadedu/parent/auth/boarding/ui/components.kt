package com.sanadedu.parent.auth.boarding.ui

import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.TextColor
import com.sanadedu.parent.theme.TrackColor

@Composable
fun ProgressIndicator(modifier: Modifier, progress: Float) {
    CircularProgressIndicator(
        modifier = modifier,
        progress = progress, //currentItem.progress
        color = DeepBlue,
        strokeWidth = 3.dp,
        trackColor = TrackColor,
        strokeCap =  StrokeCap.Round
    )
}

@Composable
fun HeaderText(modifier: Modifier, header: Int) {
    Text(
        modifier = modifier,
        text = stringResource(id = header), // Update with your string resource
        textAlign = TextAlign.Center,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        color = TextColor, // Text color
        fontSize = 28.sp,
        lineHeight = 36.sp // Adjust line height as needed
    )
}

@Composable
fun BodyText(body: Int, modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = body), // Update with your string resource
        textAlign = TextAlign.Center,
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        color = TextColor, // Text color
        fontSize = 18.sp,
        lineHeight = 24.sp, // Adjust line height as needed
        letterSpacing = 0.4.sp, // Letter spacing
    )
}

@Composable
fun PropertyImage(modifier: Modifier, property: Int) {
    Image(
        modifier = modifier,
        imageVector = ImageVector.vectorResource(id = property),
        contentDescription = stringResource(id = R.string.boarding_image)
    )
}