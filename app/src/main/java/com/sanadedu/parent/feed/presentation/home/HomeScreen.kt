package com.sanadedu.parent.feed.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sanadedu.parent.R
import com.sanadedu.parent.core.presentation.components.EmptyState
import com.sanadedu.parent.core.presentation.components.LoadingState
import com.sanadedu.parent.core.presentation.components.SessionItem
import com.sanadedu.parent.core.presentation.components.StudentCard
import com.sanadedu.parent.core.presentation.ui.fontFamily
import com.sanadedu.parent.feed.presentation.home.events.HomeFeedEvent
import com.sanadedu.parent.feed.presentation.home.items.SessionItem
import com.sanadedu.parent.feed.presentation.home.items.StudentItem
import com.sanadedu.parent.student.domain.util.StudentDataObject
import com.sanadedu.parent.student.representation.student_courses.info.data.StudentInfo
import com.sanadedu.parent.theme.DeepBlue
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    onStudentClicked: () -> Unit,
//    onProfileSelected: () -> Unit,
    viewModel: FeedViewModel = viewModel()
) {
    val state by viewModel.state

    Box(modifier = Modifier
        .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    ){
        if (state.isLoading) LoadingState()
        else if (state.isEmpty) EmptyState()
        else ActiveState(
            modifier = Modifier.padding(top = 40.dp),
            studentsList = state.students,
            sessionsList = state.sessions,
            viewModel = viewModel,
            onStudentClicked = onStudentClicked
        )
    }
}

@Composable
fun ActiveState(
    modifier: Modifier = Modifier,
    studentsList: List<StudentItem>,
    sessionsList: List<SessionItem>,
    viewModel: FeedViewModel,
    onStudentClicked: () -> Unit
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(24.dp))

        HorizontalPagerSample(
            modifier = Modifier,
            studentsList = studentsList,
            viewModel = viewModel,
            onStudentClicked = onStudentClicked
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.weekly_sessions_header),
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = DeepBlue,
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            content = {
                items(sessionsList) { session ->
                    SessionItem(
                        modifier = Modifier,
                        session = session
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerSample(
    modifier: Modifier,
    studentsList: List<StudentItem>,
    viewModel: FeedViewModel,
    onStudentClicked: () -> Unit
) {
    val pageCount = studentsList.size
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        pageCount
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel
                .onEvent(
                    HomeFeedEvent.GetAttendanceRecord(
                        cardID = studentsList[page].cardID
                    )
                )
        }
    }


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                snapVelocityThreshold = 5.dp
            ),
            pageSpacing = 8.dp,
            userScrollEnabled = true,
            beyondBoundsPageCount = 0,
        ) { page ->
            Card(
                Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        alpha = lerp(
                            start = 0.4f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                val student = studentsList[page]
                StudentCard(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .clickable {
                            StudentDataObject.setStudent(studentInfo = StudentInfo(name = student.name, code = student.code, cardId = student.cardID))
                            onStudentClicked()
                        },
                    student = student
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        HorizontalPagerIndicator(
            pageCount = pageCount,
            currentPage = pagerState.currentPage,
            targetPage = pagerState.targetPage,
            currentPageOffsetFraction = pagerState.currentPageOffsetFraction
        )
    }
}

@Composable
private fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = DeepBlue,
    unselectedIndicatorSize: Dp = 8.dp,
    selectedIndicatorSize: Dp = 10.dp,
    indicatorCornerRadius: Dp = 2.dp,
    indicatorPadding: Dp = 2.dp
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentSize()
            .height(selectedIndicatorSize + indicatorPadding * 2)
    ) {

        // draw an indicator for each page
        repeat(pageCount) { page ->
            // calculate color and size of the indicator
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    // calculate page offset
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    // calculate offset percentage between 0.0 and 1.0
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val size =
                        unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)

                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    indicatorColor.copy(alpha = 0.1f) to unselectedIndicatorSize
                }

            // draw indicator
            Box(
                modifier = Modifier
                    .padding(
                        // apply horizontal padding, so that each indicator is same width
                        horizontal = ((selectedIndicatorSize + indicatorPadding * 2) - size) / 2,
                        vertical = size / 4
                    )
                    .clip(RoundedCornerShape(indicatorCornerRadius))
                    .background(color)
                    .width(size)
                    .height(size / 2)
            )
        }
    }
}