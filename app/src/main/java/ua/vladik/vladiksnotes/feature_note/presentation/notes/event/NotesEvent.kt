package ua.vladik.vladiksnotes.feature_note.presentation.notes.event

import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.util.NoteColor

sealed class NotesEvent {

    data class GetNotesWithColor(val noteColor: NoteColor): NotesEvent()
    data class DeleteNotes(val notes: List<Note>): NotesEvent()
    data class SelectOrUnselectItem(val note: Note): NotesEvent()
    data class UpdateColor(val noteColor: NoteColor): NotesEvent()
    data class Searching(val query: String): NotesEvent()
    data object RestoreDeletedNotes: NotesEvent()
    data object ChangeEditMode: NotesEvent()
    data object ChangeSearchMode: NotesEvent()
    data object SelectOrUnselectAllItems: NotesEvent()
    data object ShowPalette: NotesEvent()
    data object ClearSearchQueryAndColor: NotesEvent()
}