package ua.vladik.vladiksnotes.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.vladik.vladiksnotes.feature_note.data.data_source.NoteDatabase
import ua.vladik.vladiksnotes.feature_note.data.repository.NoteRepositoryImpl
import ua.vladik.vladiksnotes.feature_note.domain.repository.NoteRepository
import ua.vladik.vladiksnotes.feature_note.domain.use_case.AddNoteUseCase
import ua.vladik.vladiksnotes.feature_note.domain.use_case.AddTrashNoteUseCase
import ua.vladik.vladiksnotes.feature_note.domain.use_case.DeleteNoteUseCase
import ua.vladik.vladiksnotes.feature_note.domain.use_case.DeleteTrashNoteUseCase
import ua.vladik.vladiksnotes.feature_note.domain.use_case.GetNoteUseCase
import ua.vladik.vladiksnotes.feature_note.domain.use_case.GetNotesUseCase
import ua.vladik.vladiksnotes.feature_note.domain.use_case.GetTrashNoteUseCase
import ua.vladik.vladiksnotes.feature_note.domain.use_case.GetTrashNotesUseCase
import ua.vladik.vladiksnotes.feature_note.domain.use_case.NoteUseCases
import ua.vladik.vladiksnotes.feature_note.domain.use_case.UpdateNoteUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            updateNoteUseCase = UpdateNoteUseCase(repository),
            getTrashNotesUseCase = GetTrashNotesUseCase(repository),
            addTrashNoteUseCase = AddTrashNoteUseCase(repository),
            deleteTrashNoteUseCase = DeleteTrashNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository),
            getTrashNoteUseCase = GetTrashNoteUseCase(repository)
        )
    }
}