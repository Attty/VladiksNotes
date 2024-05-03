package ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.state

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
