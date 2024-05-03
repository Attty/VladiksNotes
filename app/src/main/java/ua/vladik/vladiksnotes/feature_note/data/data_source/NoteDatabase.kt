package ua.vladik.vladiksnotes.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote

@Database(
    entities = [
        Note::class,
        TrashNote::class
    ],
    version = 2
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}