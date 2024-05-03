package ua.vladik.vladiksnotes.feature_note.presentation.read_note.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.event.AddEditNoteEvent
import ua.vladik.vladiksnotes.feature_note.presentation.read_note.components.EditModeBottomBarItem
import ua.vladik.vladiksnotes.feature_note.presentation.read_note.viewmodel.ReadNoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadNote(
    navController: NavController,
    viewModel: ReadNoteViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(viewModel.trashNote!!.color)
                    ),
                    title = {
                        Text(
                            "Only read",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineMedium
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
                Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
            }
        },
        bottomBar = {
            EditModeBottomBarItem(
                color = viewModel.trashNote.color,
                onDeleteClick = {
                    viewModel.deleteNote()
                    navController.popBackStack()
                },
                onMoveToClick = {
                    viewModel.restoreNote()
                    navController.popBackStack()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(viewModel.trashNote.color))
                .padding(it)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = viewModel.trashNote.title,
                hint = "",
                onValueChange = {
                },
                onFocusChange = {

                },
                isHintVisible = false,
                textStyle = MaterialTheme.typography.headlineLarge,
                readMode = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = viewModel.trashNote.content,
                hint = "",
                onValueChange = {
                },
                onFocusChange = {

                },
                isHintVisible = false,
                textStyle = MaterialTheme.typography.bodyMedium,
                readMode = true
            )
        }
    }


}