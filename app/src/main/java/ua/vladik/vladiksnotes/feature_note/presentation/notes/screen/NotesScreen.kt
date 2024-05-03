package ua.vladik.vladiksnotes.feature_note.presentation.notes.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ua.vladik.vladiksnotes.feature_note.domain.util.NoteColor
import ua.vladik.vladiksnotes.feature_note.presentation.notes.viewmodel.NotesViewModel
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.EditModeBottomBar
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.EditModeTopAppBar
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.NoteItem
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.Palette
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.TopAppBarDefault
import ua.vladik.vladiksnotes.feature_note.presentation.notes.event.NotesEvent
import ua.vladik.vladiksnotes.feature_note.presentation.util.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val lazyListState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    BackHandler(
        state.isEditingMode
    ) {
        viewModel.onEvent(NotesEvent.ChangeEditMode)
    }
    BackHandler(
        state.isSearchingMode
    ) {
        viewModel.onEvent(NotesEvent.ChangeSearchMode)
    }
    if (state.isEditingMode) {
        Scaffold(
            topBar = {
                EditModeTopAppBar(
                    selectedItems = state.selectedNotes,
                    disableEditMode = { viewModel.onEvent(NotesEvent.ChangeEditMode) },
                    selectAllItems = { viewModel.onEvent(NotesEvent.SelectOrUnselectAllItems) },
                    isSelectedAllItems = state.isSelectedAllNotes
                )
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
                ) {
                    EditModeBottomBar(
                        state.selectedNotes,
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNotes(state.selectedNotes))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    "Notes moved to trash",
                                    actionLabel = "Undo",
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreDeletedNotes)
                                }
                            }

                        },
                        onMoveToClick = { viewModel.onEvent(NotesEvent.ShowPalette) })
                }
            }
        ) { paddingValues ->
            if (state.showPalette) {
                Palette(
                    onDismissRequest = { viewModel.onEvent(NotesEvent.ShowPalette) },
                    onResetClick = {

                    },
                    onColorClick = {
                        viewModel.onEvent(NotesEvent.ShowPalette)
                        viewModel.onEvent(NotesEvent.UpdateColor(it))
                    },
                    isFilter = false
                )
            }
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                itemsIndexed(state.notes) { _, note ->
                    val isSelected = state.selectedNotes.contains(note)
                    NoteItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .combinedClickable(
                                onClick = {
                                    viewModel.onEvent(NotesEvent.SelectOrUnselectItem(note))
                                },
                            ), note = note, isSelected = isSelected
                    )
                }
            }
        }
    } else if (!state.isEditingMode && !state.isSearchingMode) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = MaterialTheme.colorScheme.background,
                    drawerContentColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        "Vladik's Notes", modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Divider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Screen.List.list.forEachIndexed { _, item ->
                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = MaterialTheme.colorScheme.background,
                                unselectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedContainerColor = MaterialTheme.colorScheme.onBackground,
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary
                            ),
                            label = { Text(item.title) },
                            selected = item.title == "Notes",
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                }
                                if (navController.currentDestination?.route != item.route) {
                                    navController.navigate(item.route)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier.padding(
                                NavigationDrawerItemDefaults.ItemPadding
                            )
                        )
                    }

                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },
                topBar = {
                    TopAppBarDefault(
                        onPaletteClick = { viewModel.onEvent(NotesEvent.ShowPalette) },
                        onSearchClick = { viewModel.onEvent(NotesEvent.ChangeSearchMode) },
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate(Screen.AddEditNoteScreen.route) },
                        shape = CircleShape,
                        containerColor = MaterialTheme.colorScheme.onBackground
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = "FAB",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            ) { paddingValues ->
                if (state.showPalette) {
                    Palette(
                        onDismissRequest = { viewModel.onEvent(NotesEvent.ShowPalette) },
                        onResetClick = {
                            viewModel.onEvent(NotesEvent.GetNotesWithColor(NoteColor.Nothing))
                            viewModel.onEvent(NotesEvent.ShowPalette)
                        },
                        onColorClick = {
                            viewModel.onEvent(NotesEvent.ShowPalette)
                            viewModel.onEvent(NotesEvent.GetNotesWithColor(it))
                        },
                        isFilter = true
                    )
                }
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    itemsIndexed(state.notes) { _, note ->
                        val isSelected = state.selectedNotes.contains(note)
                        NoteItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                                .combinedClickable(
                                    onClick = {
                                        navController.navigate(
                                            Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
                                        )
                                    },
                                    onLongClick = {
                                        viewModel.onEvent(NotesEvent.ChangeEditMode)
                                        viewModel.onEvent(NotesEvent.SelectOrUnselectItem(note))
                                    }
                                ), note = note, isSelected = isSelected
                        )
                    }
                }
            }
        }
    } else if (state.isSearchingMode) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            focusRequester.captureFocus()
        }
        Scaffold(
            topBar = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        SearchBar(
                            query = state.searchQuery,
                            onQueryChange = {
                                viewModel.onEvent(NotesEvent.Searching(it))
                            },
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .fillMaxWidth(0.8f),
                            onSearch = { viewModel.onEvent(NotesEvent.Searching(it)) },
                            active = false,
                            onActiveChange = { },
                            colors = SearchBarDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.onBackground,
                                inputFieldColors = TextFieldDefaults.colors(
                                    focusedTextColor = MaterialTheme.colorScheme.primary
                                )
                            ),
                            placeholder = {
                                Text(
                                    "Search",
                                    color = MaterialTheme.colorScheme.secondary,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            },
                            leadingIcon = {
                                IconButton(onClick = { viewModel.onEvent(NotesEvent.ChangeSearchMode) }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            trailingIcon = {
                                IconButton(onClick = { viewModel.onEvent(NotesEvent.ShowPalette) }) {
                                    Icon(
                                        imageVector = Icons.Default.Palette,
                                        contentDescription = "Palette",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                        ) {}
                        TextButton(onClick = { viewModel.onEvent(NotesEvent.ClearSearchQueryAndColor) }) {
                            Text(
                                "Undo",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                    Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
                }
            }
        ) {
            if (state.showPalette) {
                Palette(
                    onDismissRequest = { viewModel.onEvent(NotesEvent.ShowPalette) },
                    onResetClick = {
                        viewModel.onEvent(NotesEvent.GetNotesWithColor(NoteColor.Nothing))
                        viewModel.onEvent(NotesEvent.ShowPalette)
                    },
                    onColorClick = {
                        viewModel.onEvent(NotesEvent.ShowPalette)
                        viewModel.onEvent(NotesEvent.GetNotesWithColor(it))
                    },
                    isFilter = true
                )
            }
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                itemsIndexed(state.notes.filter { note ->
                    note.title.contains(state.searchQuery, true)
                        .or(note.content.contains(state.searchQuery, true))
                }) { _, note ->
                    val isSelected = state.selectedNotes.contains(note)
                    NoteItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .combinedClickable(
                                onClick = {
                                    navController.navigate(
                                        Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
                                    )
                                },
                            ), note = note, isSelected = isSelected
                    )
                }
            }

        }


    }

}