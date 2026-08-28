package com.sanadedu.parent.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.getDayString
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.feed.presentation.home.items.SessionItem
import com.sanadedu.parent.feed.presentation.home.items.StudentItem
import com.sanadedu.parent.theme.DeepBlue
import com.sanadedu.parent.theme.ErrorRed
import com.sanadedu.parent.theme.LightCautionYellow
import com.sanadedu.parent.theme.LightErrorRed
import com.sanadedu.parent.theme.LightRed
import com.sanadedu.parent.theme.LightSuccessGreen
import com.sanadedu.parent.theme.MidNightBlue
import com.sanadedu.parent.theme.SubTextColor
import com.sanadedu.parent.theme.SuccessGreen
import com.sanadedu.parent.theme.TextColor

@Composable
fun NotificationButton(
    modifier: Modifier = Modifier,
    hasNotifications: Boolean = true
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_bell),
            contentDescription = stringResource(id = R.string.bell_icon),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 12.dp, top = 11.dp)
                .size(10.dp)
                .background(
                    if (hasNotifications) LightRed
                    else Color.Transparent,
                    shape = CircleShape
                )
                .alpha(0.8f),
            text = " ",
        )
    }
}

@Composable
fun StudentCard(
    modifier: Modifier,
    student: StudentItem
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(top = 30.dp)
                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = student.name,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = MidNightBlue
                )
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Student Code",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = student.code,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue,
                            fontSize = 12.sp
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Courses",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = student.coursesCount,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue,
                            fontSize = 12.sp
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Status",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "NA",
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = DeepBlue,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
//            CircularIndicator(
//                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp, end = 16.dp),
//                size = 72.dp,
//                strokeWidth = 6.dp,
//                text = student.avgAttendance,
//                fontSize = 16.sp,
//                progress = 0.35f,//Integer.parseInt(student.avgAttendance).toFloat() / 100
//                textColor = CautionYellow,
//                solidColor = CautionYellow,
//                trackColor = LightCautionYellow
//            )
        }
        Image(
            modifier = Modifier
                .size(64.dp)
                .background(color = Color.White, shape = CircleShape)
                .align(Alignment.TopCenter),
            imageVector = ImageVector.vectorResource(R.drawable.sanad_logo),
            contentDescription = stringResource(id = R.string.student_image)
        )
    }
}


@Composable
fun SessionItem(
    modifier: Modifier = Modifier,
    session: SessionItem
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(top = 20.dp, bottom = 20.dp, start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (session.isAttended) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = LightSuccessGreen, shape = CircleShape)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = stringResource(id = R.string.student_image)
            )
        } else if (session.isPending){
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = LightCautionYellow, shape = CircleShape)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.ic_pending),
                contentDescription = stringResource(id = R.string.student_image)
            )
        } else {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = LightErrorRed, shape = CircleShape)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = stringResource(id = R.string.student_image)
            )
        }

        Column(modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = session.courseName,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    fontSize = 14.sp
                )
                GradientChip(
                    modifier = Modifier.padding(start = 4.dp),
                    text = session.centerName
                )
                GradientChip(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(id = R.string.group) + session.groupNumber
                )
            }
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = session.tutorName,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = SubTextColor,
                    fontSize = 13.sp
                )
                SolidDeepBlueChip(
                    modifier = Modifier.padding(start = 4.dp),
                    text = getDayString(session.dayOfWeek)
                )
                SolidDeepBlueChip(
                    modifier = Modifier.padding(start = 4.dp),
                    text = session.hour
                )
            }
        }
//        CircularIndicatocourseDatar(
//            modifier = Modifier.padding(top = 18.dp, bottom = 18.dp, end = 16.dp),
//            size = 56.dp,
//            strokeWidth = 4.dp,
//            text = "?",
//            fontSize = 12.sp,
//            progress = 0.35f,
//            textColor = CautionYellow,
//            solidColor = CautionYellow,
//            trackColor = LightCautionYellow
//        )
    }
}

@Composable
fun TabLayout(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(32.dp)
            .background(
                color = DeepBlue,
                shape = RoundedCornerShape(24.dp)
            )
    ){
        Row(
            modifier = modifier
                .padding(1.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(start = 8.dp, end = 8.dp),
                text = "Sessions",
                textAlign = TextAlign.Center,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                color = DeepBlue,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = DeepBlue,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(start = 8.dp, end = 8.dp),
                text = "Exams",
                textAlign = TextAlign.Center,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun CourseItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            painter = painterResource(id = R.drawable.img_course_hold),
            contentDescription = stringResource(id = R.string.student_image)
        )
        Column(modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Physics 1 Sec.",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    color = TextColor,
                    fontSize = 14.sp
                )
                GradientChip(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "Group 04"
                )
            }
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mr/ Ahmed Mohammed",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = SubTextColor,
                    fontSize = 9.sp
                )
                SolidDeepBlueChip(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "El Forsan"
                )
            }
        }
        CircularIndicator(
            modifier = Modifier.padding(top = 18.dp, bottom = 18.dp, end = 16.dp),
            size = 56.dp,
            strokeWidth = 4.dp,
            text = "45%",
            fontSize = 12.sp,
            progress = 0.45f,
            textColor = SuccessGreen,
            solidColor = SuccessGreen,
            trackColor = LightSuccessGreen
        )
    }
}

@Composable
fun ExamItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .weight(1f)
        ) {
            Text(
                text = "Exam 01",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                color = TextColor,
                fontSize = 18.sp
            )

            Row(
                modifier = Modifier.padding(top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SolidDeepBlueChip(
                    modifier = Modifier,
                    text = "El Forsan"
                )
                SolidDeepBlueChip(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "El Forsan"
                )
                SolidDeepBlueChip(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "El Forsan"
                )
                SolidDeepBlueChip(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "El Forsan"
                )
            }
        }
        CircularIndicator(
            modifier = Modifier.padding(top = 18.dp, bottom = 18.dp, end = 16.dp),
            size = 56.dp,
            strokeWidth = 4.dp,
            text = "5/20",
            fontSize = 12.sp,
            progress = 0.25f,
            textColor = ErrorRed,
            solidColor = ErrorRed,
            trackColor = LightErrorRed
        )
    }
}
