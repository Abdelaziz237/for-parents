package com.sanadedu.parent.student.representation.single_course

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.appbars.BasicAppBar
import com.sanadedu.parent.core.presentation.components.CircularIndicator
import com.sanadedu.parent.core.presentation.components.GradientChip
import com.sanadedu.parent.core.presentation.components.LoadingState
import com.sanadedu.parent.core.presentation.components.SolidDeepBlueChip
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.student.representation.single_course.data.ExamItemForCourse
import com.sanadedu.parent.student.representation.single_course.data.SessionItemForCourse
import com.sanadedu.parent.student.representation.student_courses.CourseElement
import com.sanadedu.parent.student.representation.student_courses.info.data.CourseItem
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.ErrorRed
import com.sanadedu.parent.theme.LightCautionYellow
import com.sanadedu.parent.theme.LightErrorRed
import com.sanadedu.parent.theme.LightSuccessGreen
import com.sanadedu.parent.theme.MidNightBlue
import com.sanadedu.parent.theme.SubTextColor
import com.sanadedu.parent.theme.SuccessGreen
import com.sanadedu.parent.theme.TextColor
import com.sanadedu.parent.theme.bl
import java.text.DecimalFormat

enum class Tab {
    SESSIONS,
    EXAMS
}

@Composable
fun CourseScreen(
    onBackPressed: () -> Unit,
    viewModel: CourseViewModel = viewModel()
) {
    val state = viewModel.state.value

    var selectedTab by remember {
        mutableStateOf(Tab.SESSIONS)
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(event = SingleCourseEvents.StartLoading)
        viewModel.onEvent(event = SingleCourseEvents.GetCourseData)
    }

    if (state.isLoading) LoadingState()
    else Box(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicAppBar(text = stringResource(id = R.string.course_details), navigateUp = onBackPressed)

            Spacer(modifier = Modifier.height(48.dp))

            CourseCard(course = state.course, avgAttendance = state.avgAttendance, avgExams = state.avgExams)

            Spacer(modifier = Modifier.height(24.dp))

            TabLayout(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(260.dp),
                onSessionsClicked = {
                    selectedTab = Tab.SESSIONS
                },
                onExamsClicked = {
                    selectedTab = Tab.EXAMS
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                when(selectedTab) {
                    Tab.SESSIONS -> {
                        items(state.sessions) { sessionItem ->
                            SessionElement(
                                modifier = Modifier
                                    .padding(10.dp),
                                session = sessionItem
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                    Tab.EXAMS -> {
                        items(state.exams) { examItem ->
                            ExamElement(
                                modifier = Modifier
                                    .padding(10.dp),
                                exam = examItem
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SessionElement(
    modifier: Modifier = Modifier,
    session: SessionItemForCourse,
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
                .background(if (session.isAttended) LightSuccessGreen else if (session.isPending) LightCautionYellow else LightErrorRed),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = if (session.isAttended) painterResource(id = R.drawable.ic_done)
                else if (session.isPending) painterResource(id = R.drawable.ic_pending)
                else painterResource(id = R.drawable.ic_close),
                contentDescription = stringResource(id = R.string.student_image)
            )
        }
        Column(modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(start = 12.dp, end = 12.dp)
            .weight(1f)
        ) {
            Row {
                Text(
                    text = session.sessionName.ifEmpty { session.courseName },
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = TextColor,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(4.dp))

                GradientChip(
                    modifier = Modifier,
                    text = stringResource(id = R.string.group) +  " " + session.groupNumber
                )
            }

            LazyRow {
                item {
                    Text(
                        text = session.tutorName,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        color = DeepBlue,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                }

                item {
                    if (session.attendedDay.isEmpty()) {
                        SolidGradient(text = stringResource(id = R.string.not_attended_yet))
                    } else {
                        SolidDeepBlueChip(
                            text = session.attendedDay
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        SolidDeepBlueChip(
                            text = session.attendedHour
                        )
                    }
                }
            }
        }

//        if (session.sessionFees.isNotEmpty()) {
//            Column(modifier = Modifier
//                .padding(top = 5.dp, start = 12.dp, end = 12.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = stringResource(id = R.string.sessionFees),
//                    fontFamily = fontFamily,
//                    fontWeight = FontWeight.Normal,
//                    color = DeepBlue,
//                    fontSize = 15.sp
//                )
//                Text(
//                    text = session.sessionFees,
//                    fontFamily = fontFamily,
//                    fontWeight = FontWeight.Bold,
//                    color = DeepBlue,
//                    fontSize = 16.sp
//                )
//            }
//        }
    }
}

@Composable
fun ExamElement(
    modifier: Modifier = Modifier,
    exam: ExamItemForCourse,
) {
    val decimalFormat = DecimalFormat("#.##")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularIndicator(
            modifier = Modifier.padding(end = 16.dp),
            size = 56.dp,
            strokeWidth = 4.dp,
            text = if (exam.grade == -1f) "X" else decimalFormat.format(exam.grade * 100) + " %",
            fontSize = 12.sp,
            progress = exam.grade,
            textColor = if (exam.grade > 0.5f) SuccessGreen else ErrorRed,
            solidColor = if (exam.grade > 0.5f) SuccessGreen else ErrorRed,
            trackColor = if (exam.grade > 0.5f) LightSuccessGreen else LightErrorRed
        )
        Column(modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(start = 12.dp, end = 12.dp)
            .weight(1f)
        ) {
            Text(
                text = exam.examName,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                color = TextColor,
                fontSize = 14.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = exam.tutorName,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = DeepBlue,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.width(4.dp))

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_separator),
                    contentDescription = stringResource(id = R.string.dot)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = exam.centerName,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = SubTextColor,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LazyRow {
                items(exam.tags) { tag ->
                    SolidGradient(text = tag)
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        Column(modifier = Modifier
            .padding(top = 5.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.exam),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                color = DeepBlue,
                fontSize = 15.sp
            )
            Text(
                text = exam.examNumber,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                color = DeepBlue,
                fontSize = 16.sp
            )
        }
    }
}


@Composable
fun SolidGradient(
    modifier: Modifier = Modifier,
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
        Text(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
            text = text,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            color = Color.White,
            fontSize = 8.sp
        )
    }
}

@Composable
fun CourseCard(
    modifier: Modifier = Modifier,
    course: CourseItem,
    avgAttendance: Float,
    avgExams: Float,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            CourseElement(
                courseItem = CourseItem(
                    courseId = course.courseId,
                    courseName = course.courseName,
                    tutorName = course.tutorName,
                    centerName = course.centerName,
                    centerCode = course.centerCode,
                    courseImg = course.courseImg,
                    sessionsCount = course.sessionsCount
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                StatsElement(
                    modifier = Modifier.weight(1f),
                    percentage = avgAttendance,
                    fieldLabel = stringResource(id = R.string.attendance_record),
                    fieldIcon = ImageVector.vectorResource(R.drawable.ic_attendance)
                )
                StatsElement(
                    modifier = Modifier.weight(1f),
                    percentage = avgExams,
                    fieldLabel = stringResource(id = R.string.exams_record),
                    fieldIcon = ImageVector.vectorResource(R.drawable.ic_exams)
                )
            }
        }
    }
}

@Composable
fun StatsElement(
    modifier: Modifier = Modifier,
    percentage: Float,
    fieldLabel: String,
    fieldIcon: ImageVector,
) {
    val decimalFormat = DecimalFormat("#.##")

    Box(
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color = Color(0xFFF1F1FA), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = fieldIcon,
                    contentDescription = fieldLabel
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = fieldLabel,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = SubTextColor,
                    fontSize = 10.sp
                )
                Text(
                    modifier = Modifier.offset(y = (-5).dp),
                    text = if (percentage < 0) "N/A" else decimalFormat.format(percentage * 100) + " %",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = MidNightBlue,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun TabLayout(
    modifier: Modifier = Modifier,
    onExamsClicked: () -> Unit,
    onSessionsClicked: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isExamsSelected by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .height(32.dp)
            .background(
                color = DeepBlue,
                shape = RoundedCornerShape(24.dp)
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(
                        color = if (!isExamsSelected) DeepBlue else Color.White,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(start = 8.dp, end = 8.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        isExamsSelected = false
                        onSessionsClicked()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.sessions),
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = if (isExamsSelected) DeepBlue else Color.White,
                    fontSize = 16.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(
                        color = if (isExamsSelected) DeepBlue else Color.White,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(start = 8.dp, end = 8.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        isExamsSelected = true
                        onExamsClicked()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.exams),
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = if (isExamsSelected) Color.White else DeepBlue,
                    fontSize = 16.sp
                )
            }
        }
    }
}

