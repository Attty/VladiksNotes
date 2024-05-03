package ua.vladik.vladiksnotes.feature_note.domain.use_case

import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.repository.NoteRepository

class UpdateNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(
        note: Note
    ){
        repository.updateNote(note)
    }
}