package ua.vladik.vladiksnotes.feature_note.domain.repository

import kotlinx.coroutines.flow.Flow
import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote

interface NoteRepository {


    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)

    fun getTrashNotes(): Flow<List<TrashNote>>

    suspend fun insertTrashNote(trashNote: TrashNote)

    suspend fun deleteTrashNote(trashNote: TrashNote)

    suspend fun getTrashNoteById(id: Int): TrashNote?

}