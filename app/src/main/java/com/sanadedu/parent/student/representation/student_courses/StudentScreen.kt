package com.sanadedu.parent.student.representation.student_courses

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.appbars.BasicAppBar
import com.sanadedu.parent.core.presentation.components.LoadingState
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.student.domain.util.CourseDataObject
import com.sanadedu.parent.student.domain.util.StudentDataObject
import com.sanadedu.parent.student.representation.student_courses.info.StudentScreenEvents
import com.sanadedu.parent.student.representation.student_courses.info.data.CenterItem
import com.sanadedu.parent.student.representation.student_courses.info.data.CourseItem
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.SubTextColor
import com.sanadedu.parent.theme.TextColor

@Composable
fun StudentScreen(
    onBackPressed: () -> Unit,
    onCourseSelected: () -> Unit,
    onNotificationsClicked: () -> Unit,
    viewModel: SingleStudentViewModel = viewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(Unit) {
        viewModel.onEvent(event = StudentScreenEvents.StartLoading)
        viewModel.onEvent(event = StudentScreenEvents.GetCenters)
        viewModel.onEvent(event = StudentScreenEvents.GetCourses)
    }

    if (state.isLoading) LoadingState()
    else Box(
        modifier = Modifier
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
                .alpha(0.6f),
            imageVector = ImageVector.vectorResource(id = R.drawable.bg_header),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        BasicAppBar(
            text = stringResource(id = R.string.student_profile),
            hasNotificationIcon = true,
            onNotificationsClicked = onNotificationsClicked,
            navigateUp = onBackPressed
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))

            Image(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.ic_student),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = StudentDataObject.getStudent().name,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            Text(
                text = StudentDataObject.getStudent().code,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

//            CenterElementsBar(
//                modifier = Modifier.align(Alignment.Start),
//                centers = state.centers,
//                onClick = { code ->
//                    viewModel.onEvent(event = StudentScreenEvents.FilterCoursesByCenter(centerCode = code))
//                }
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = R.string.student_courses),
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Color.Black
            )

            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                items(state.courses) { course ->
                    CourseElement(
                        modifier = Modifier
                            .clickable {
                                Log.e("course", course.toString())
                                CourseDataObject.setCourse(
                                    course = course
                                )
                                onCourseSelected()
                            }
                            .padding(10.dp),
                        courseItem = course
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
fun CenterElementsBar(
    modifier: Modifier = Modifier,
    centers: List<CenterItem>,
    onClick: (code: String) -> Unit,
) {
    var selectedCenter by remember {
        mutableStateOf("")
    }
    LazyRow(
        modifier = modifier
    ) {
        items(centers) {
            CenterElement(
                modifier = Modifier
                    .clickable {
                        selectedCenter = it.code
                        onClick(it.code)
                    },
                center = it,
                isSelected = it.code == selectedCenter
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun CenterElement(
    modifier: Modifier,
    center: CenterItem,
    isSelected: Boolean
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(56.dp))
            .padding(1.dp)
            .then(
                if (isSelected) {
                    Modifier.background(DeepBlue, shape = RoundedCornerShape(56.dp))
                } else {
                    Modifier
                        .border(width = 1.dp, color = DeepBlue, shape = RoundedCornerShape(56.dp))
                        .background(Color.White, shape = RoundedCornerShape(56.dp))
                }
            )
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            text = center.name,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            color = if (isSelected) Color.White else DeepBlue,
            fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CourseElement(
    modifier: Modifier = Modifier,
    courseItem: CourseItem,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        ) {
            if (courseItem.courseImg.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.img_course_hold),
                    contentDescription = stringResource(id = R.string.student_image)
                )
            } else {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    model = courseItem.courseImg,
                    contentDescription = stringResource(id = R.string.course_img)
                )
            }
        }
        Column(modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .weight(1f)
        ) {
            Text(
                text = courseItem.courseName,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                color = TextColor,
                fontSize = 14.sp
            )

            LazyRow(verticalAlignment = Alignment.CenterVertically) {
                item {
                    Text(
                        text = courseItem.tutorName,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        color = DeepBlue,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                }

                item {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_separator),
                        contentDescription = stringResource(id = R.string.dot)
                    )

                    Spacer(modifier = Modifier.width(5.dp))
                }

                item {
                    Text(
                        text = courseItem.centerName,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        color = SubTextColor,
                        fontSize = 11.sp
                    )
                }
            }
        }
        Column(modifier = Modifier
            .padding(top = 5.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = courseItem.sessionsCount,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                color = DeepBlue,
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier.offset(y = (-5).dp),
                text = stringResource(id = R.string.sessions),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                color = DeepBlue,
                fontSize = 12.sp
            )
        }
    }
}
