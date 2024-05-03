package ua.vladik.vladiksnotes.feature_note.presentation.trash.event

import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.presentation.notes.event.NotesEvent

sealed class TrashEvent {
    data class DeleteNotes(val notes: List<TrashNote>) : TrashEvent()
    data class SelectOrUnselectItem(val note: TrashNote): TrashEvent()
    data object GetDeletedNotes : TrashEvent()
    data object ChangeEditMode: TrashEvent()
    data object SelectOrUnselectAllItems: TrashEvent()
    data object ShowBottomSheet: TrashEvent()
    data class RestoreNotes(val notes: List<TrashNote>): TrashEvent()

}