package com.sanad.studentsapp.core.presentation.boarding.info

sealed class BoardingEvent {
    object ShowNext: BoardingEvent()
}