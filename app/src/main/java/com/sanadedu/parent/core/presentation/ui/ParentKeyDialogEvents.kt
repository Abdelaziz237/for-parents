package com.sanadedu.parent.core.presentation.ui

sealed class ParentKeyDialogEvents {
    data object StartLoading: ParentKeyDialogEvents()
    data class AddStudentByKey(val parentKey: String): ParentKeyDialogEvents()
    data object StopLoading: ParentKeyDialogEvents()
}