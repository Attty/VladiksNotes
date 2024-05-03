package ua.vladik.vladiksnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.screen.AddEditNote
import ua.vladik.vladiksnotes.feature_note.presentation.notes.screen.NotesScreen
import ua.vladik.vladiksnotes.feature_note.presentation.read_note.screen.ReadNote
import ua.vladik.vladiksnotes.feature_note.presentation.settings.SettingsScreen
import ua.vladik.vladiksnotes.feature_note.presentation.trash.screen.TrashScreen
import ua.vladik.vladiksnotes.feature_note.presentation.util.Screen

import ua.vladik.vladiksnotes.ui.theme.VladiksNotesTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            VladiksNotesTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}&readMode={readMode}",
                            arguments = listOf(
                                navArgument(
                                    "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument("readMode") {
                                    type = NavType.BoolType
                                    defaultValue = false
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNote(
                                navController,
                                color
                            )
                        }
                        composable(route = Screen.SettingsScreen.route) {
                            SettingsScreen(navController)
                        }
                        composable(route = Screen.TrashScreen.route) {
                            TrashScreen(navController)
                        }
                        composable(route = Screen.ReadNoteScreen.route +
                                "?noteId={noteId}",
                            arguments = listOf(
                                navArgument(
                                    "noteId"
                                ) {
                                    type = NavType.IntType
                                },

                            )
                        ) {
                            ReadNote(navController = navController)
                        }

                    }
                }
            }
        }
    }
}
