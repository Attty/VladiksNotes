package ua.vladik.vladiksnotes.feature_note.presentation.trash.state

import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote

data class TrashState(
    val notes: List<TrashNote> = emptyList(),
    val isEditingMode: Boolean = false,
    val selectedNotes: List<TrashNote> = emptyList(),
    val showBottomSheet: Boolean = false,
    val isSelectedAllNotes: Boolean = false
)