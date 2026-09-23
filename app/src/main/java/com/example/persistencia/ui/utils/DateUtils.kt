package com.example.persistencia.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Utilidades de fecha usadas por el calendario y las pantallas nuevas.
 * No reemplaza ni toca la lógica de persistencia: solo formatea/calcula
 * a partir del timestamp (fechaCreacion) que ya trae cada Task.
 */
object DateUtils {

    val monthsEs = listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )

    // Lunes a Domingo, igual que el mockup
    val weekdaysShortEs = listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")

    private val weekdaysFullEs = listOf(
        "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
    )

    private val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    fun formatDateTime(millis: Long): String = dateTimeFormat.format(Date(millis))

    fun daysInMonth(year: Int, month: Int): Int {
        val cal = Calendar.getInstance()
        cal.set(year, month, 1)
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    /** Offset (0 = Lunes) del primer día del mes, para alinear la cuadrícula. */
    fun firstDayOffset(year: Int, month: Int): Int {
        val cal = Calendar.getInstance()
        cal.set(year, month, 1)
        // Calendar.DAY_OF_WEEK: Domingo=1 ... Sábado=7 -> convertir a Lunes=0
        val dow = cal.get(Calendar.DAY_OF_WEEK)
        return (dow + 5) % 7
    }

    fun weekdayName(year: Int, month: Int, day: Int): String {
        val cal = Calendar.getInstance()
        cal.set(year, month, day, 0, 0, 0)
        return weekdaysFullEs[cal.get(Calendar.DAY_OF_WEEK) - 1]
    }

    fun currentYearMonthDay(): Triple<Int, Int, Int> {
        val cal = Calendar.getInstance()
        return Triple(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
    }
}
