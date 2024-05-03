package ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.event

import androidx.compose.ui.focus.FocusState
import ua.vladik.vladiksnotes.feature_note.domain.util.NoteColor

sealed class AddEditNoteEvent {
    data class EnteredTitle(val value: String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()

    data class EnteredContent(val value: String) : AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()

    data class ChangeColor(val color: NoteColor) : AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()



}