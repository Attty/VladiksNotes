package ua.vladik.vladiksnotes.feature_note.domain.use_case

import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.domain.repository.NoteRepository

class GetTrashNoteUseCase (
    private val repository: NoteRepository
){
    suspend operator fun invoke(id: Int): TrashNote? {
        return repository.getTrashNoteById(id)
    }
}