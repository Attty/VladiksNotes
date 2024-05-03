package ua.vladik.vladiksnotes.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    color: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .background(Color(color))
            .border(
                BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.primary
                )
            ).
        clickable { onClick() }
    )

}