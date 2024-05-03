package ua.vladik.vladiksnotes.feature_note.domain.use_case

import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.domain.repository.NoteRepository

class AddTrashNoteUseCase(
    val repository: NoteRepository
) {

    suspend operator fun invoke(note: TrashNote){
        repository.insertTrashNote(note)
    }
}