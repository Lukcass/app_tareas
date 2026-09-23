package com.example.persistencia.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.persistencia.ui.theme.AppColors

/**
 * Menú flotante de acciones de una tarea (sustituye a los dos IconButton
 * fijos debajo de la tarjeta). Aparece flotando sobre la interfaz al
 * pulsar el botón "⋮" de TaskCard.
 *
 * Preparado para 2 acciones principales (Actualizar / Eliminar) y ya
 * incluye una tercera (Consultar) fácilmente ampliable con más botones.
 */
@Composable
fun FloatingTaskMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onViewTask: () -> Unit,
    onEditTask: () -> Unit,
    onDeleteTask: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        shape = com.example.persistencia.ui.theme.AppShapes.MenuShape,
        modifier = Modifier.padding(4.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Consultar") },
            leadingIcon = { Icon(Icons.Filled.Visibility, contentDescription = null, tint = AppColors.InkSoft) },
            onClick = onViewTask
        )
        DropdownMenuItem(
            text = { Text("Actualizar") },
            leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = null, tint = AppColors.Accent) },
            onClick = onEditTask
        )
        DropdownMenuItem(
            text = { Text("Eliminar", color = AppColors.Danger) },
            leadingIcon = { Icon(Icons.Filled.Delete, contentDescription = null, tint = AppColors.Danger) },
            onClick = onDeleteTask
        )
    }
}
