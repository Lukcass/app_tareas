package com.example.persistencia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.persistencia.data.Task
import com.example.persistencia.ui.components.EmptyTasksView
import com.example.persistencia.ui.components.TaskCalendar
import com.example.persistencia.ui.components.TaskCard
import com.example.persistencia.ui.theme.AppColors
import com.example.persistencia.ui.utils.DateUtils
import com.example.persistencia.ui.utils.TaskDateFilter

/**
 * Pantalla "Buscar por fecha": título, selector de mes, calendario,
 * fecha seleccionada y lista de tareas de ese día.
 * Consume la lista de tareas real que ya entrega el TaskViewModel
 * (no crea una fuente de datos independiente).
 */
@Composable
fun TaskSearchScreen(
    tasks: List<Task>,
    onViewTask: (Task) -> Unit,
    onEditTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    val (todayYear, todayMonth, todayDay) = remember { DateUtils.currentYearMonthDay() }
    var year by remember { mutableIntStateOf(todayYear) }
    var month by remember { mutableIntStateOf(todayMonth) }
    var selectedDay by remember { mutableIntStateOf(todayDay) }

    val daysWithTasks = remember(tasks, year, month) {
        TaskDateFilter.daysWithTasksInMonth(tasks, year, month)
    }
    val dayTasks = remember(tasks, year, month, selectedDay) {
        TaskDateFilter.tasksForDay(tasks, year, month, selectedDay)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Text(
                text = "Buscar por fecha",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                color = AppColors.Ink,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
            )

            TaskCalendar(
                year = year,
                month = month,
                selectedDay = selectedDay,
                daysWithTasks = daysWithTasks,
                onDaySelected = { selectedDay = it },
                onPrevMonth = {
                    if (month == 0) { month = 11; year -= 1 } else month -= 1
                    selectedDay = 1
                },
                onNextMonth = {
                    if (month == 11) { month = 0; year += 1 } else month += 1
                    selectedDay = 1
                },
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            HorizontalDivider(color = AppColors.Line, modifier = Modifier.padding(top = 16.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
                Text(
                    text = "SELECCIONADO",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.InkSoft
                )
                Text(
                    text = selectedDay.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.Ink
                )
                Text(
                    text = "${DateUtils.weekdayName(year, month, selectedDay)} · ${dayTasks.size} tareas",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.InkSoft
                )
            }
        }

        if (dayTasks.isEmpty()) {
            item { EmptyTasksView("Sin tareas ese día.") }
        } else {
            items(dayTasks, key = { it.id }) { task ->
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
