package ua.vladik.vladiksnotes.feature_note.presentation.trash.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.domain.use_case.NoteUseCases
import ua.vladik.vladiksnotes.feature_note.presentation.trash.event.TrashEvent
import ua.vladik.vladiksnotes.feature_note.presentation.trash.state.TrashState
import javax.inject.Inject

@HiltViewModel
class TrashViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TrashState())
    val state: State<TrashState> = _state

    private var getDeletedNotesJob: Job? = null

    init {
        getDeletedNotes()
    }


    fun onEvent(event: TrashEvent) {
        when (event) {
            is TrashEvent.GetDeletedNotes -> {
                getDeletedNotes()
            }

            is TrashEvent.DeleteNotes -> {
                event.notes.forEach {
                    deleteNote(it)
                }
                changeEditMode()
            }

            is TrashEvent.ChangeEditMode -> {
                changeEditMode()
            }

            is TrashEvent.SelectOrUnselectItem -> {
                selectOrUnselectItem(event.note)
                isSelectedAllItems()

            }

            is TrashEvent.SelectOrUnselectAllItems -> {
                isSelectedAllItems()
                selectOrUnselectAllItems()
            }

            is TrashEvent.ShowBottomSheet -> {
                showBottomSheet()
            }

            is TrashEvent.RestoreNotes -> {
                restoreNotes(event.notes)
                changeEditMode()
            }
        }
    }

    private fun restoreNotes(notes: List<TrashNote>) {
        viewModelScope.launch {
            notes.forEach { trashNote ->
                noteUseCases.addNoteUseCase(trashNote.toNote())
                noteUseCases.deleteTrashNoteUseCase(trashNote)
            }
        }
    }

    private fun showBottomSheet() {
        _state.value = state.value.copy(
            showBottomSheet = !_state.value.showBottomSheet
        )
    }

    private fun unselectAllItems() {
        _state.value = state.value.copy(
            selectedNotes = emptyList(),
            isSelectedAllNotes = false
        )
    }

    private fun selectAllItems() {
        _state.value = state.value.copy(
            selectedNotes = _state.value.notes,
            isSelectedAllNotes = true
        )
    }

    private fun isSelectedAllItems() {
        if (_state.value.selectedNotes.containsAll(_state.value.notes)) {
            _state.value = state.value.copy(
                isSelectedAllNotes = true
            )
        } else {
            _state.value = state.value.copy(
                isSelectedAllNotes = false
            )
        }
    }

    private fun selectOrUnselectAllItems() {
        if (!_state.value.isSelectedAllNotes) {
            selectAllItems()
        } else {
            unselectAllItems()
        }
    }

    private fun selectOrUnselectItem(note: TrashNote) {
        if (_state.value.selectedNotes.contains(note)) {
            _state.value = state.value.copy(
                selectedNotes = _state.value.selectedNotes.filter { it != note }
            )
        } else {
            val list = _state.value.selectedNotes.toMutableList()
            list.add(note)
            _state.value = state.value.copy(
                selectedNotes = list
            )
        }
    }

    private fun changeEditMode() {
        if (_state.value.isEditingMode) {
            _state.value = state.value.copy(
                isEditingMode = false,
                selectedNotes = emptyList()
            )
        } else {
            _state.value = state.value.copy(
                isEditingMode = true
            )
        }
    }

    private fun deleteNote(note: TrashNote) {
        viewModelScope.launch {
            noteUseCases.deleteTrashNoteUseCase(note)
        }
    }

    private fun getDeletedNotes() {
        getDeletedNotesJob?.cancel()
        getDeletedNotesJob = noteUseCases.getTrashNotesUseCase().onEach { notes ->
            _state.value = state.value.copy(
                notes = notes
            )
        }
            .launchIn(viewModelScope)
    }
}