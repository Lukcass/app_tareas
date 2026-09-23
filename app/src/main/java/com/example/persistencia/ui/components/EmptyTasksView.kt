package com.example.persistencia.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.persistencia.ui.theme.AppColors

/** Estado vacío reutilizable para cuando no hay tareas que mostrar. */
@Composable
fun EmptyTasksView(message: String = "Sin tareas registradas.") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.InkFaint,
            textAlign = TextAlign.Center
        )
    }
}
