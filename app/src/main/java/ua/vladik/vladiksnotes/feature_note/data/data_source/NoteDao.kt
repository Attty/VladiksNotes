package ua.vladik.vladiksnotes.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(entity = Note::class,
        onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Delete(entity = Note::class)
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM trashnote")
    fun getTrashNotes(): Flow<List<TrashNote>>

    @Query("SELECT * FROM trashnote WHERE id = :id")
    suspend fun getTrashNoteById(id: Int): TrashNote?

    @Insert(entity = TrashNote::class,
        onConflict = OnConflictStrategy.REPLACE
        )
    suspend fun insertTrashNote(trashNote: TrashNote)

    @Delete(entity = TrashNote::class)
    suspend fun deleteTrashNote(trashNote: TrashNote)

}