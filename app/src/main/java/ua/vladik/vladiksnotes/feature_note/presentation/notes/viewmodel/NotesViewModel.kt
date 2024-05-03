package ua.vladik.vladiksnotes.feature_note.presentation.notes.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.model.TrashNote
import ua.vladik.vladiksnotes.feature_note.domain.use_case.NoteUseCases
import ua.vladik.vladiksnotes.feature_note.domain.util.NoteColor
import ua.vladik.vladiksnotes.feature_note.presentation.notes.event.NotesEvent
import ua.vladik.vladiksnotes.feature_note.presentation.notes.state.NotesState
import ua.vladik.vladiksnotes.ui.theme.Blue
import ua.vladik.vladiksnotes.ui.theme.Cyan
import ua.vladik.vladiksnotes.ui.theme.Gray
import ua.vladik.vladiksnotes.ui.theme.Green
import ua.vladik.vladiksnotes.ui.theme.LightBlue
import ua.vladik.vladiksnotes.ui.theme.LightGray
import ua.vladik.vladiksnotes.ui.theme.Magenta
import ua.vladik.vladiksnotes.ui.theme.Orange
import ua.vladik.vladiksnotes.ui.theme.Pink
import ua.vladik.vladiksnotes.ui.theme.Red
import ua.vladik.vladiksnotes.ui.theme.White
import ua.vladik.vladiksnotes.ui.theme.Yellow
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNotes: MutableList<TrashNote> = mutableListOf()

    private var getNotesJob: Job? = null

    init {
        getNotes(noteColor = NoteColor.Nothing)
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.GetNotesWithColor -> {
                if (state.value.noteColor::class == event.noteColor::class) {
                    return
                }
                getNotes(event.noteColor)
            }

            is NotesEvent.DeleteNotes -> {
                deleteNotes(event.notes)
                changeEditMode()
            }

            is NotesEvent.ChangeEditMode -> {
                changeEditMode()
            }

            is NotesEvent.SelectOrUnselectAllItems -> {
                isSelectedAllItems()
                selectOrUnselectAllItems()
            }

            is NotesEvent.SelectOrUnselectItem -> {
                selectOrUnselectItem(event.note)
                isSelectedAllItems()
            }

            is NotesEvent.ShowPalette -> {
                showPalette()
            }

            is NotesEvent.UpdateColor -> {
                updateColor(event.noteColor)
                changeEditMode()
            }

            is NotesEvent.ChangeSearchMode -> {
                changeSearchMode()
            }

            is NotesEvent.Searching -> {
                searching(event.query)
            }

            is NotesEvent.ClearSearchQueryAndColor -> {
                clearSearchQuery_Color()
            }

            is NotesEvent.RestoreDeletedNotes -> {
                restoreDeletedNotes()
            }
        }
    }
    private fun selectOrUnselectAllItems(){
        if (!_state.value.isSelectedAllNotes) {
            selectAllItems()
        } else {
            unselectAllItems()
        }
    }

    private fun selectOrUnselectItem(note: Note) {
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

    private fun showPalette() {
        _state.value = state.value.copy(
            showPalette = !_state.value.showPalette
        )
    }

    private fun updateColor(noteColor: NoteColor) {
        val list = mutableListOf<Note>()
        _state.value.selectedNotes.forEach {
            list.add(
                it.copy(
                    color = when (noteColor) {
                        is NoteColor.White -> White.toArgb()
                        is NoteColor.Red -> Red.toArgb()
                        is NoteColor.Orange -> Orange.toArgb()
                        is NoteColor.Yellow -> Yellow.toArgb()
                        is NoteColor.Green -> Green.toArgb()
                        is NoteColor.LightBlue -> LightBlue.toArgb()
                        is NoteColor.Magenta -> Magenta.toArgb()
                        is NoteColor.Cyan -> Cyan.toArgb()
                        is NoteColor.Blue -> Blue.toArgb()
                        is NoteColor.Pink -> Pink.toArgb()
                        is NoteColor.LightGray -> LightGray.toArgb()
                        is NoteColor.Gray -> Gray.toArgb()
                        is NoteColor.Nothing -> White.toArgb()
                    }
                )
            )
        }
        viewModelScope.launch {
            list.forEach {
                noteUseCases.updateNoteUseCase(it)
            }
        }
    }

    private fun deleteNotes(list: List<Note>) {
        recentlyDeletedNotes.clear()
        viewModelScope.launch {
            list.forEach { note ->
                noteUseCases.deleteNoteUseCase(note)
                recentlyDeletedNotes.add(note.toTrashNote())
                noteUseCases.addTrashNoteUseCase(note.toTrashNote())
            }
        }
    }

    private fun restoreDeletedNotes() {
        viewModelScope.launch {
            recentlyDeletedNotes.forEach {
                noteUseCases.deleteTrashNoteUseCase(it)
                noteUseCases.addNoteUseCase(it.toNote())
            }
            recentlyDeletedNotes.clear()
        }
    }

    private fun clearSearchQuery_Color() {
        _state.value = state.value.copy(
            searchQuery = "",
            noteColor = NoteColor.Nothing
        )
        getNotes(_state.value.noteColor)
    }

    private fun searching(query: String) {
        _state.value = state.value.copy(
            searchQuery = query
        )
    }

    private fun changeSearchMode() {
        if (_state.value.isSearchingMode) {
            _state.value = state.value.copy(
                isSearchingMode = false,
                searchQuery = ""
            )
            println("search mode = false")
        } else {
            _state.value = state.value.copy(
                isSearchingMode = true
            )
            println("search mode = true")
        }
    }

    private fun unselectAllItems() {
        _state.value = state.value.copy(
            selectedNotes = emptyList(),
            isSelectedAllNotes = false
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

    private fun selectAllItems() {
        _state.value = state.value.copy(
            selectedNotes = _state.value.notes,
            isSelectedAllNotes = true
        )
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


    private fun getNotes(noteColor: NoteColor) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(noteColor).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteColor = noteColor
            )
        }
            .launchIn(viewModelScope)
    }
}