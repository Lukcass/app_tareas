package com.example.persistencia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.persistencia.data.Task
import com.example.persistencia.ui.components.EmptyTasksView
import com.example.persistencia.ui.components.TaskCard
import com.example.persistencia.ui.theme.AppColors

/**
 * Pantalla "Todas las tareas". Recibe la lista real desde TaskViewModel
 * (ya viene ordenada "más nuevas primero" por el propio TaskDao).
 */
@Composable
fun AllTasksScreen(
    tasks: List<Task>,
    onViewTask: (Task) -> Unit,
    onEditTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        item {
            Text(
                text = "Todas las tareas",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                color = AppColors.Ink,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
            )
            Text(
                text = "Más nuevas primero",
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.InkSoft,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (tasks.isEmpty()) {
            item { EmptyTasksView("No hay tareas registradas. Pulsa + para agregar una.") }
        } else {
            items(tasks, key = { it.id }) { task ->
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
                    TaskCard(
                        task = task,
                        onView = { onViewTask(task) },
                        onEdit = { onEditTask(task) },
                        onDelete = { onDeleteTask(task) }
                    )
                }
            }
        }
    }
}
