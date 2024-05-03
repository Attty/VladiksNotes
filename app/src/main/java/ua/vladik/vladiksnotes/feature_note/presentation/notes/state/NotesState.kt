package ua.vladik.vladiksnotes.feature_note.presentation.notes.state

import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.domain.util.NoteColor

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteColor: NoteColor = NoteColor.Nothing,
    val isEditingMode: Boolean = false,
    val selectedNotes: List<Note> = emptyList(),
    val isSelectedAllNotes: Boolean = false,
    val showPalette: Boolean = false,
    val isSearchingMode: Boolean = false,
    val searchQuery: String = "",
)
