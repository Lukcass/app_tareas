package com.example.persistencia.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun TaskAppScreen(taskViewModel: TaskViewModel = viewModel()) {
    val tasks by taskViewModel.tasks.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    var taskToView by remember { mutableStateOf<Task?>(null) }

    // Estado del calendario de "Buscar" elevado aquí: no se reinicia
    // al cambiar de pestaña y volver.
    val (todayYear, todayMonth, todayDay) = remember { DateUtils.currentYearMonthDay() }
    var searchYear by remember { mutableIntStateOf(todayYear) }
    var searchMonth by remember { mutableIntStateOf(todayMonth) }
    var searchSelectedDay by remember { mutableIntStateOf(todayDay) }

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
        // Las tres pantallas se componen una sola vez y permanecen vivas;
        // cambiar de pestaña solo cambia cuál ocupa espacio (0.dp = oculta
        // pero no destruida), evitando reconstruir el calendario cada vez.
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Box(modifier = if (selectedTab == 0) Modifier.fillMaxSize() else Modifier.size(0.dp)) {
                TaskSearchScreen(
                    tasks = tasks,
                    year = searchYear,
                    month = searchMonth,
                    selectedDay = searchSelectedDay,
                    onYearMonthChange = { y, m ->
                        searchYear = y
                        searchMonth = m
                        searchSelectedDay = 1
                    },
                    onDaySelected = { searchSelectedDay = it },
                    onToggleTask = { taskViewModel.toggleTaskState(it) },
                    onViewTask = { taskToView = it },
                    onEditTask = { taskToEdit = it },
                    onDeleteTask = { taskViewModel.deleteTask(it) }
                )
            }
            Box(modifier = if (selectedTab == 1) Modifier.fillMaxSize() else Modifier.size(0.dp)) {
                AllTasksScreen(
                    tasks = tasks,
                    onToggleTask = { taskViewModel.toggleTaskState(it) },
                    onViewTask = { taskToView = it },
                    onEditTask = { taskToEdit = it },
                    onDeleteTask = { taskViewModel.deleteTask(it) }
                )
            }
            Box(modifier = if (selectedTab == 2) Modifier.fillMaxSize() else Modifier.size(0.dp)) {
                CompletedTasksScreen(
                    tasks = tasks,
                    onToggleTask = { taskViewModel.toggleTaskState(it) },
                    onViewTask = { taskToView = it },
                    onEditTask = { taskToEdit = it },
                    onDeleteTask = { taskViewModel.deleteTask(it) }
                )
            }
        }
    }

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