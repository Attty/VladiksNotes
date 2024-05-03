package ua.vladik.vladiksnotes.feature_note.domain.use_case

import kotlinx.coroutines.flow.Flow
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.domain.repository.NoteRepository

class GetTrashNotesUseCase(val repository: NoteRepository) {

    operator fun invoke(): Flow<List<TrashNote>> {
        return repository.getTrashNotes()
    }
}