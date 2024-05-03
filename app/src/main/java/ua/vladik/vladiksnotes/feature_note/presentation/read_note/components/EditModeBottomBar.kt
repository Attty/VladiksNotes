package ua.vladik.vladiksnotes.feature_note.presentation.read_note.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ua.vladik.vladiksnotes.feature_note.domain.model.Note

@Composable
fun EditModeBottomBarItem(
    isDeleteScreen: Boolean = false,
    color: Int,
    onDeleteClick: () -> Unit,
    onMoveToClick: () -> Unit
) {
    Column {
        Divider(
            color = Color(color),
            thickness = 1.dp
        )
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.background,
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(!isDeleteScreen){
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .clickable() { onMoveToClick.invoke() },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                modifier = Modifier.rotate(90f),
                                imageVector = Icons.Default.Restore, contentDescription = "Restore",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "Restore",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodySmall
                            )

                        }
                    }else {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .clickable() { onMoveToClick.invoke() },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                imageVector = Icons.Default.Restore, contentDescription = "Move To",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "Restore",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodySmall
                            )

                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .clickable() { onDeleteClick.invoke() },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete, contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Delete",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall
                        )

                    }

                }
            }
        )
    }


}