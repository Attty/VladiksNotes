package ua.vladik.vladiksnotes.feature_note.domain.use_case

import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.domain.repository.NoteRepository

class DeleteTrashNoteUseCase(
    val repository: NoteRepository
) {

    suspend operator fun invoke(trashNote: TrashNote) {
        repository.deleteTrashNote(trashNote)
    }
}