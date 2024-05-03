package ua.vladik.vladiksnotes.feature_note.presentation.trash.screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.EditModeBottomBar
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.EditModeTopAppBar
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.NoteItem
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.TopAppBarDefault
import ua.vladik.vladiksnotes.feature_note.presentation.notes.event.NotesEvent
import ua.vladik.vladiksnotes.feature_note.presentation.trash.components.NoteTrashItem
import ua.vladik.vladiksnotes.feature_note.presentation.trash.event.TrashEvent
import ua.vladik.vladiksnotes.feature_note.presentation.trash.viewmodel.TrashViewModel
import ua.vladik.vladiksnotes.feature_note.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TrashScreen(
    navController: NavController,
    viewModel: TrashViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current

    if (state.isEditingMode) {
        Scaffold(
            topBar = {
                EditModeTopAppBar(
                    selectedItems = state.selectedNotes,
                    disableEditMode = { viewModel.onEvent(TrashEvent.ChangeEditMode) },
                    selectAllItems = { viewModel.onEvent(TrashEvent.SelectOrUnselectAllItems) },
                    isSelectedAllItems = state.isSelectedAllNotes
                )
            },
            bottomBar = {
                EditModeBottomBar(
                    list = state.selectedNotes,
                    onDeleteClick = {
                        viewModel.onEvent(TrashEvent.ShowBottomSheet)
                                    },
                    isDeleteScreen = true,
                    onMoveToClick = {
                        viewModel.onEvent(TrashEvent.RestoreNotes(state.selectedNotes))
                        Toast.makeText(context, "Notes restored", Toast.LENGTH_SHORT).show()

                    }
                )
            }
        ) {
            if (state.showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.onEvent(TrashEvent.ShowBottomSheet) },
                    sheetState = sheetState,
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.25f)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "Delete Notes",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = if (state.selectedNotes.size > 1) {
                                "Are you sure you want to delete these notes? This action can't be rolled back."
                            } else {
                                "Are you sure you want to delete this note? This action can't be rolled back."
                            },
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            viewModel.onEvent(TrashEvent.ShowBottomSheet)
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(
                                    "Cancel",
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Button(
                                onClick = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            viewModel.onEvent(TrashEvent.ShowBottomSheet)
                                            viewModel.onEvent(TrashEvent.DeleteNotes(state.selectedNotes))
                                            Toast.makeText(context, "Notes deleted", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(
                                    "Delete",
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(state.notes) { note ->
                    val isSelected = state.selectedNotes.contains(note)
                    NoteTrashItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .clickable {
                                viewModel.onEvent(TrashEvent.SelectOrUnselectItem(note))
                            }, note = note, isSelected = isSelected
                    )
                }
            }
        }
    } else if (!state.isEditingMode) {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            ),
                        title = {
                            Text(
                                "Recycle Bin",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )
                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        ) { paddingValues ->
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                itemsIndexed(state.notes) { _, note ->
                    val isSelected = state.selectedNotes.contains(note)
                    NoteTrashItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .combinedClickable(
                                onClick = {
                                    navController.navigate(
                                        Screen.ReadNoteScreen.route + "?noteId=${note.id}"
                                    )
                                },
                                onLongClick = {
                                    viewModel.onEvent(TrashEvent.ChangeEditMode)
                                    viewModel.onEvent(TrashEvent.SelectOrUnselectItem(note))
                                }
                            ), note = note, isSelected = isSelected
                    )
                }
            }

        }
    }

}