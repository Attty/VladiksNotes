package ua.vladik.vladiksnotes.feature_note.data.repository

import kotlinx.coroutines.flow.Flow
import ua.vladik.vladiksnotes.feature_note.data.data_source.NoteDao
import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }


    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        return dao.updateNote(note)
    }

    override fun getTrashNotes(): Flow<List<TrashNote>> {
        return dao.getTrashNotes()
    }

    override suspend fun insertTrashNote(trashNote: TrashNote) {
        return dao.insertTrashNote(trashNote)
    }

    override suspend fun deleteTrashNote(trashNote: TrashNote) {
        return dao.deleteTrashNote(trashNote)
    }

    override suspend fun getTrashNoteById(id: Int): TrashNote? {
        return dao.getTrashNoteById(id)
    }
}