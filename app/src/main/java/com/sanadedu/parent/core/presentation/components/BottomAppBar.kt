package com.sanadedu.parent.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sanadedu.parent.R
import com.sanadedu.parent.core.data.Screen
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.IdealTextColor
import com.sanadedu.parent.theme.bl

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    onHomePressed: () -> Unit,
    onProfilePressed: () -> Unit,
    onAddStudentPressed: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    var isHomeActive by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            isHomeActive = when (destination.route) {
                Screen.MainScreen.HomeScreen.route -> {
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .background(color = Color.White)
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            onProfilePressed()
                        }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = if (!isHomeActive) ImageVector.vectorResource(id = R.drawable.ic_profile)
                        else ImageVector.vectorResource(id = R.drawable.ic_profile_ideal),
                    contentDescription = stringResource(id = R.string.bell_icon),
                )
                Text(
                    text = stringResource(id = R.string.profile),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = if (!isHomeActive) DeepBlue else IdealTextColor
                )
            }
            Spacer (modifier = Modifier.weight(0.5f))
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            onHomePressed()
                        }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector =
                        if (isHomeActive) ImageVector.vectorResource(id = R.drawable.ic_home_active)
                        else ImageVector.vectorResource(id = R.drawable.ic_home_ideal),
                    contentDescription = stringResource(id = R.string.bell_icon),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Home",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = if (isHomeActive) DeepBlue else IdealTextColor
                )
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(56.dp),
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            onClick = {
                onAddStudentPressed()
            }
        ) {
            Image(
                modifier = Modifier
                    .size(64.dp)
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
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = stringResource(id = R.string.plus)
            )
        }
    }
}