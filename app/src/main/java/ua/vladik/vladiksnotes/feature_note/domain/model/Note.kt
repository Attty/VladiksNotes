package ua.vladik.vladiksnotes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
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


@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val color: Int,
    @PrimaryKey
    val id: Int? = null
){
    fun toTrashNote():TrashNote{
        return TrashNote(
            title = this.title,
            content = this.content,
            color = this.color,
            id = this.id
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)