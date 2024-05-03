package ua.vladik.vladiksnotes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ua.vladik.vladiksnotes.feature_note.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditModeTopAppBar(
    selectedItems: List<Any>,
    disableEditMode: () -> Unit,
    selectAllItems: () -> Unit,
    isSelectedAllItems: Boolean
) {
    Column {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                navigationIconContentColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.primary,
                actionIconContentColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text(
                    text = if (selectedItems.isEmpty()) {
                        "Select Items"
                    } else {
                        "Selected: ${selectedItems.size}"
                    },
                    style = MaterialTheme.typography.headlineLarge
                )
            },
            navigationIcon = {
                IconButton(onClick = { disableEditMode.invoke() }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Disable edit mode")
                }
            },
            actions = {
                IconButton(onClick = { selectAllItems.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Checklist, contentDescription = "Select all items",
                        tint = if (isSelectedAllItems) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    )
                }
            }
        )
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp
        )
    }
}