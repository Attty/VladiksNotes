package ua.vladik.vladiksnotes.feature_note.domain.use_case

import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.vladik.vladiksnotes.feature_note.domain.model.Note
import ua.vladik.vladiksnotes.feature_note.domain.repository.NoteRepository
import ua.vladik.vladiksnotes.feature_note.domain.util.NoteColor
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

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteColor: NoteColor?
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteColor){
                is NoteColor.White -> notes.filter { it.color == White.toArgb() }
                is NoteColor.Red -> notes.filter { it.color == Red.toArgb() }
                is NoteColor.Orange -> notes.filter { it.color == Orange.toArgb() }
                is NoteColor.Yellow -> notes.filter { it.color == Yellow.toArgb() }
                is NoteColor.Green -> notes.filter { it.color == Green.toArgb() }
                is NoteColor.LightBlue -> notes.filter { it.color == LightBlue.toArgb() }
                is NoteColor.Magenta -> notes.filter { it.color == Magenta.toArgb() }
                is NoteColor.Cyan -> notes.filter { it.color == Cyan.toArgb() }
                is NoteColor.Blue -> notes.filter { it.color == Blue.toArgb() }
                is NoteColor.Pink -> notes.filter { it.color == Pink.toArgb() }
                is NoteColor.LightGray -> notes.filter { it.color == LightGray.toArgb() }
                is NoteColor.Gray -> notes.filter { it.color == Gray.toArgb() }
                else -> notes
            }
        }
    }
}