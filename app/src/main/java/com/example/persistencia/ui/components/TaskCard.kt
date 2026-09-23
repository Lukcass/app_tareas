package com.example.persistencia.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.persistencia.data.Task
import com.example.persistencia.ui.theme.AppColors
import com.example.persistencia.ui.theme.AppShapes
import com.example.persistencia.ui.utils.DateUtils

/**
 * Tarjeta visual de una tarea (equivalente al TaskItem de TaskScreen.kt
 * original, pero con el estilo del mockup y el menú de acciones flotante
 * en vez de botones fijos). Usa el modelo Task tal cual existe.
 */
@Composable
fun TaskCard(
    task: Task,
    onView: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        shape = AppShapes.CardShape,
        colors = CardDefaults.cardColors(containerColor = AppColors.CardWhite),
        border = BorderStroke(1.dp, AppColors.Line),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 13.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = DateUtils.formatDateTime(task.fechaCreacion),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.AccentStrong
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = task.titulo,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Ink,
                    textDecoration = if (task.estadoCompletado) TextDecoration.LineThrough else TextDecoration.None
                )
                if (task.descripcion.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = task.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppColors.InkSoft
                    )
                }
            }

            Box {
                IconButton(onClick = { menuExpanded = true }, modifier = Modifier.size(30.dp)) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Acciones de la tarea",
                        tint = AppColors.InkSoft
                    )
                }
                FloatingTaskMenu(
                    expanded = menuExpanded,
                    onDismiss = { menuExpanded = false },
                    onViewTask = { menuExpanded = false; onView() },
                    onEditTask = { menuExpanded = false; onEdit() },
                    onDeleteTask = { menuExpanded = false; onDelete() }
                )
            }
        }
    }
}
