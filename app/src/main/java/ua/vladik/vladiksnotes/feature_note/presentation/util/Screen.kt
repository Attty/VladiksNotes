package ua.vladik.vladiksnotes.feature_note.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.ReadMore
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object NotesScreen: Screen(
        route = "notes_screen",
        title = "Notes",
        icon = Icons.Default.Bookmarks,
    )
    object AddEditNoteScreen: Screen(
        route = "add_edit_note_screen",
        title = "Add or Edit Note",
        icon = Icons.Default.Edit
    )
    object SettingsScreen: Screen(
        route = "settings_screen",
        title = "Settings",
        icon = Icons.Default.Settings
    )
    object TrashScreen: Screen(
        route = "trash_screen",
        title = "Recycle Bin",
        icon = Icons.Default.Delete
    )
    object ReadNoteScreen: Screen(
        route = "read_note",
        title = "Read Note",
        icon = Icons.Default.ReadMore
    )
    object List{
        val list = listOf(
            NotesScreen,
            TrashScreen,
            SettingsScreen
        )
    }
}