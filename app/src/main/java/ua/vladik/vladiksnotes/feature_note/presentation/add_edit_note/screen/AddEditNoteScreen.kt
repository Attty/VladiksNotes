package ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.screen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ua.vladik.vladiksnotes.feature_note.domain.util.NoteColor
import ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.components.ColorBox
import ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.event.AddEditNoteEvent
import ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.viewmodel.AddEditNoteViewModel
import ua.vladik.vladiksnotes.feature_note.presentation.notes.components.Palette
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNote(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val showPalette = remember {
        mutableStateOf(false)
    }

    val noteBackGroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }

        }
    }
    if (showPalette.value) {
        Palette(
            onDismissRequest = { showPalette.value = false },
            onResetClick = { /*TODO*/ },
            onColorClick = {
                val color = when (it) {
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
                scope.launch {
                    noteBackGroundAnimatable.animateTo(
                        targetValue = Color(color),
                        animationSpec = tween(
                            durationMillis = 500
                        )
                    )
                }
                viewModel.onEvent(AddEditNoteEvent.ChangeColor(it))
                showPalette.value = false
            },
            isFilter = false
        )
    }
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = noteBackGroundAnimatable.value,
                        navigationIconContentColor = MaterialTheme.colorScheme.primary,
                        actionIconContentColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        ColorBox(
                            modifier = Modifier.padding(end = 32.dp),
                            color = viewModel.noteColor.value
                        ) {
                            showPalette.value = true
                        }
                    }
                )
                Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) },
                containerColor = MaterialTheme.colorScheme.onBackground
            ) {
                Icon(
                    imageVector = Icons.Default.Save, contentDescription = "Save note",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackGroundAnimatable.value)
                .padding(it)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                textStyle = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
