package ua.vladik.vladiksnotes.feature_note.presentation.notes.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ua.vladik.vladiksnotes.feature_note.domain.util.NoteColor
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

@Composable
fun Palette(
    onDismissRequest: () -> Unit,
    onResetClick: () -> Unit,
    onColorClick: (NoteColor) -> Unit,
    isFilter: Boolean
) {
    val list = listOf(
        White.toArgb(),
        Red.toArgb(),
        Orange.toArgb(),
        Yellow.toArgb(),
        Green.toArgb(),
        LightBlue.toArgb(),
        Magenta.toArgb(),
        Cyan.toArgb(),
        Blue.toArgb(),
        Pink.toArgb(),
        LightGray.toArgb(),
        Gray.toArgb(),
    )
    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Card(
            modifier = Modifier
                .height(350.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.primary
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = if(isFilter) Arrangement.SpaceEvenly else Arrangement.Top
            ) {
                Text(
                    text = if (isFilter) {
                        "Filter by color"
                    } else {
                        "Move to"
                    },
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineMedium
                )
                if(!isFilter){
                    Spacer(modifier = Modifier.height(32.dp))
                }
                if (isFilter) {
                    ColorItem(text = "Reset", color = White.toArgb(),
                        modifier = Modifier
                            .size(width = 90.dp, height = 40.dp)
                            .clickable { onResetClick.invoke() })
                }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        items(list) { color ->
                            ColorItem(color = color,
                                modifier = Modifier
                                    .size(width = 64.dp, height = 48.dp)
                                    .padding(4.dp)
                                    .clickable {
                                        when (color) {
                                            White.toArgb() -> onColorClick(NoteColor.White)
                                            Red.toArgb() -> onColorClick(NoteColor.Red)
                                            Orange.toArgb() -> onColorClick(NoteColor.Orange)
                                            Yellow.toArgb() -> onColorClick(NoteColor.Yellow)
                                            Green.toArgb() -> onColorClick(NoteColor.Green)
                                            LightBlue.toArgb() -> onColorClick(NoteColor.LightBlue)
                                            Magenta.toArgb() -> onColorClick(NoteColor.Magenta)
                                            Cyan.toArgb() -> onColorClick(NoteColor.Cyan)
                                            Blue.toArgb() -> onColorClick(NoteColor.Blue)
                                            Pink.toArgb() -> onColorClick(NoteColor.Pink)
                                            LightGray.toArgb() -> onColorClick(NoteColor.LightGray)
                                            Gray.toArgb() -> onColorClick(NoteColor.Gray)
                                        }
                                    })
                        }
                    }
                }
            }
        }
    }


@Composable
fun ColorItem(
    modifier: Modifier = Modifier,
    text: String? = null,
    color: Int,
) {

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            if (color == White.toArgb()) {
                drawRoundRect(
                    Color.Black,
                    size = size,
                    cornerRadius = CornerRadius(50f),
                    style = Stroke(
                        width = 5f
                    )
                )
            }
            drawRoundRect(
                Color(color),
                cornerRadius = CornerRadius(50f)
            )
        }
        text?.let {
            Text(
                it, modifier = Modifier
                    .align(Alignment.Center)
                    .padding(4.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}