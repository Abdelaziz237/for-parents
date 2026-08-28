package com.sanadedu.parent.auth.boarding.ui.boarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sanad.studentsapp.core.presentation.boarding.info.BoardingEvent
import com.sanadedu.parent.auth.boarding.info.BoardingState
import com.sanadedu.parent.R

class BoardingViewModel: ViewModel() {
    private var items: List<BoardingState> = listOf(
        BoardingState(
            image = R.drawable.property2,
            header = R.string.header_2,
            body = R.string.body_2,
            progress = 0.66f
        ),
        BoardingState(
            image = R.drawable.property3,
            header = R.string.header_3,
            body = R.string.body_3,
            progress = 1f
        ),
    )
    private var i = 0

    private val _state = mutableStateOf(BoardingState())
    val state: State<BoardingState> = _state

    fun onEvent(event: BoardingEvent, callback: () -> Unit) {
        if (event is BoardingEvent.ShowNext) {
            if (i == items.size) {
                callback()
            }else {
                _state.value = state.value.copy(
                    image = items[i].image,
                    header = items[i].header,
                    body = items[i].body,
                    progress = items[i].progress
                )
                i++
            }
        }
    }
}