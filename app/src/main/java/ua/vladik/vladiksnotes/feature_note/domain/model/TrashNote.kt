package ua.vladik.vladiksnotes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrashNote(
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val color: Int,
    @PrimaryKey
    val id: Int? = null
){
    fun toNote():Note{
        return Note(
            title = this.title,
            content = this.content,
            color = this.color,
            id = this.id
        )
    }
}