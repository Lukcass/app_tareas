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

/** Pantalla "Completadas": filtra en memoria, sin tocar el DAO. */
@Composable
fun CompletedTasksScreen(
    tasks: List<Task>,
    onToggleTask: (Task) -> Unit,
    onViewTask: (Task) -> Unit,
    onEditTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    val completedTasks = tasks.filter { it.estadoCompletado }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Completadas",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            color = AppColors.Ink,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
        )

        Box(modifier = Modifier.weight(1f)) {
            if (completedTasks.isEmpty()) {
                EmptyTasksView("Aún no completas tareas.")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 96.dp)
                ) {
                    items(completedTasks, key = { it.id }) { task ->
                        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
                            TaskCard(
                                task = task,
                                onToggle = { onToggleTask(task) },
                                onView = { onViewTask(task) },
                                onEdit = { onEditTask(task) },
                                onDelete = { onDeleteTask(task) }
                            )
                        }
                    }
                }
            }
        }
    }
}