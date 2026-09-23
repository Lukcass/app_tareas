package com.example.persistencia.ui.utils

import com.example.persistencia.data.Task
import java.util.Calendar

/**
 * Filtra en memoria la lista de tareas que ya entrega el TaskViewModel
 * (repository.allTasks). No crea una segunda fuente de datos ni toca
 * la base de datos: solo agrupa/filtra por fecha usando fechaCreacion.
 */
object TaskDateFilter {

    /** Tareas cuya fechaCreacion cae exactamente en year/month/day, más recientes primero. */
    fun tasksForDay(tasks: List<Task>, year: Int, month: Int, day: Int): List<Task> {
        val cal = Calendar.getInstance()
        return tasks.filter { task ->
            cal.timeInMillis = task.fechaCreacion
            cal.get(Calendar.YEAR) == year &&
                cal.get(Calendar.MONTH) == month &&
                cal.get(Calendar.DAY_OF_MONTH) == day
        }.sortedByDescending { it.fechaCreacion }
    }

    /** Días (1..31) del mes indicado que tienen al menos una tarea, para el indicador del calendario. */
    fun daysWithTasksInMonth(tasks: List<Task>, year: Int, month: Int): Set<Int> {
        val cal = Calendar.getInstance()
        val result = mutableSetOf<Int>()
        for (task in tasks) {
            cal.timeInMillis = task.fechaCreacion
            if (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) == month) {
                result.add(cal.get(Calendar.DAY_OF_MONTH))
            }
        }
        return result
    }
}
