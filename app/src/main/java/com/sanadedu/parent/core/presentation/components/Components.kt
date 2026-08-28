package com.sanadedu.parent.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.LightGray
import com.sanadedu.parent.theme.MidNightBlue
import com.sanadedu.parent.theme.bl

@Composable
fun LoadBackgroundPattern() {
    Image(
        modifier = Modifier
            .fillMaxSize() // Replace with desired width and height
            .alpha(0.13f),
        contentScale = ContentScale.FillBounds,
        imageVector = ImageVector.vectorResource(id = R.drawable.pattern),
        contentDescription = stringResource(id = R.string.bg_pattern)
    )
}

@Composable
fun LoadProgressIndicator(
    size: Dp,
    progress: Float,
    solidColor: Color,
    trackColor: Color
) {
    CircularProgressIndicator(
        modifier = Modifier
            .size(size),
        progress = progress,
        color = solidColor, // Use MaterialTheme color
        strokeWidth = 3.dp, // Adjust stroke width
        trackColor = trackColor, // Set track color
        strokeCap =  StrokeCap.Round
    )
}

@Composable
fun CircularIndicator(
    modifier: Modifier,
    size: Dp,
    strokeWidth: Dp,
    text: String,
    progress: Float,
    textColor: Color,
    fontSize: TextUnit,
    solidColor: Color,
    trackColor: Color
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size),
            progress = progress,
            color = solidColor, // Use MaterialTheme color
            strokeWidth = strokeWidth,
            trackColor = trackColor, // Set track color
            strokeCap =  StrokeCap.Round
        )
        Text(
            text = text,
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            fontSize = fontSize
        )
    }
}

@Composable
fun MainButton(
    modifier: Modifier,
    text: String,
    isEnable: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 16.dp, end = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DeepBlue
        ),
        enabled = isEnable,
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Composable
fun CustomCheckbox(
    modifier: Modifier,
    isEditable: Boolean = true,
    isAccepted : (Boolean) -> Unit
) {
    var checked by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (checked) DeepBlue else Color.White)
            .border(
                width = 1.dp,
                color = LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                if (isEditable) {
                    checked = !checked
                    isAccepted(checked)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = ColorPainter(Color.Transparent),
            contentDescription = "Unchecked"
        )
        AnimatedVisibility(
            visible = checked,
            exit = shrinkOut(shrinkTowards = Alignment.TopStart) + fadeOut()
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                contentDescription = "Checked"
            )
        }
    }
}


@Composable
fun GradientChip(
    modifier: Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                brush = Brush.horizontalGradient(listOf(DeepBlue, bl)),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(1.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                modifier = Modifier.padding(start = 6.dp, end = 6.dp),
                text = text,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                color = DeepBlue,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun SolidDeepBlueChip(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .padding(1.dp)
            .background(DeepBlue, shape = RoundedCornerShape(8.dp))
    ) {
        Text(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp),
            text = text,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            fontSize = 11.sp
        )
    }
}

@Composable
fun SolidWhiteChip(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .padding(1.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = DeepBlue, shape = RoundedCornerShape(8.dp))
    ) {
        Text(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp),
            text = text,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            color = DeepBlue,
            fontSize = 11.sp
        )
    }
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(64.dp),
            color = bl,
            strokeWidth = 6.dp,
            trackColor = LightGray,
            strokeCap = StrokeCap.Round
        )
    }
}


@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_no_data),
                contentDescription = stringResource(id = R.string.no_data)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.no_students),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MidNightBlue,
                fontSize = 24.sp
            )
        }
    }
}