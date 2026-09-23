package com.example.persistencia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.persistencia.data.Task
import com.example.persistencia.ui.components.TaskBottomNavigation
import com.example.persistencia.ui.theme.AppColors
import com.example.persistencia.ui.components.TaskFormDialog
import com.example.persistencia.ui.theme.TaskViewModel
import com.example.persistencia.ui.utils.DateUtils

/**
 * Nuevo punto de entrada de la interfaz de "Mis tareas".
 * Reutiliza TaskViewModel y TaskFormDialog tal cual existen hoy:
 * no se modifica ni el modelo, ni el DAO, ni el ViewModel, ni el
 * formulario de creación/edición. Solo se agrega la navegación,
 * el calendario y las tarjetas nuevas encima de lo que ya existe.
 */
@Composable
fun TaskAppScreen(taskViewModel: TaskViewModel = viewModel()) {
    val tasks by taskViewModel.tasks.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    var taskToView by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        containerColor = AppColors.Background,
        bottomBar = {
            TaskBottomNavigation(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = AppColors.AccentStrong,
                contentColor = androidx.compose.ui.graphics.Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar tarea")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> TaskSearchScreen(
                    tasks = tasks,
                    onViewTask = { taskToView = it },
                    onEditTask = { taskToEdit = it },
                    onDeleteTask = { taskViewModel.deleteTask(it) }
                )
                else -> AllTasksScreen(
                    tasks = tasks,
                    onViewTask = { taskToView = it },
                    onEditTask = { taskToEdit = it },
                    onDeleteTask = { taskViewModel.deleteTask(it) }
                )
            }
        }
    }

    // Reutiliza el mismo TaskFormDialog existente en TaskScreen.kt: mismos
    // campos (título, descripción), misma llamada a addTask()/updateTask().
    if (showAddDialog) {
        TaskFormDialog(
            title = "Nueva Tarea",
            initialTitulo = "",
            initialDescripcion = "",
            onDismiss = { showAddDialog = false },
            onConfirm = { titulo, descripcion ->
                taskViewModel.addTask(titulo, descripcion)
                showAddDialog = false
            }
        )
    }

    taskToEdit?.let { task ->
        TaskFormDialog(
            title = "Editar Tarea",
            initialTitulo = task.titulo,
            initialDescripcion = task.descripcion,
            onDismiss = { taskToEdit = null },
            onConfirm = { titulo, descripcion ->
                taskViewModel.updateTask(task, titulo, descripcion)
                taskToEdit = null
            }
        )
    }

    taskToView?.let { task ->
        TaskDetailDialog(task = task, onDismiss = { taskToView = null })
    }
}

/** Diálogo simple de "Consultar" (solo lectura), sin tocar el modelo Task. */
@Composable
private fun TaskDetailDialog(task: Task, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(task.titulo) },
        text = {
            Column {
                Text(
                    text = DateUtils.formatDateTime(task.fechaCreacion),
                    style = MaterialTheme.typography.labelMedium,
                    color = AppColors.AccentStrong
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (task.descripcion.isNotBlank()) task.descripcion else "Sin descripción.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.InkSoft
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (task.estadoCompletado) "Estado: completada" else "Estado: pendiente",
                    style = MaterialTheme.typography.labelMedium,
                    color = AppColors.InkSoft
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Cerrar") }
        }
    )
}
