package ua.vladik.vladiksnotes.feature_note.presentation.read_note.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.domain.use_case.NoteUseCases
import javax.inject.Inject

@HiltViewModel
class ReadNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var trashNote: TrashNote = TrashNote("", "", 0, 0, 0)

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            viewModelScope.launch {
                noteUseCases.getTrashNoteUseCase(noteId)?.also {
                    trashNote = it
                }
            }
        }


    }

    fun deleteNote() {
        viewModelScope.launch {
            noteUseCases.deleteTrashNoteUseCase(trashNote)
        }
    }

    fun restoreNote() {
        viewModelScope.launch {
            noteUseCases.addNoteUseCase(trashNote.toNote())
            noteUseCases.deleteTrashNoteUseCase(trashNote)
        }
    }
}