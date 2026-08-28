package com.sanadedu.parent.core.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.components.LoadBackgroundPattern
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.MidNightBlueLight

@Composable
fun TryAgainScreen(
    onTryAgain: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        LoadBackgroundPattern()

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .widthIn(max = 280.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(256.dp),
                painter = painterResource(id = R.drawable.img_sunrise),
                contentDescription = null
            )

            Text(
                modifier = Modifier.offset(y = -(48).dp),
                text = stringResource(id = R.string.failed_to_load_page),
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                color = MidNightBlueLight,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .offset(y = (-32).dp)
                    .clip(shape = RoundedCornerShape(80.dp))
                    .clickable(
                        onClick = { onTryAgain() }
                    )
                    .background(
                        color = DeepBlue,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_refresh),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.try_again),
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}