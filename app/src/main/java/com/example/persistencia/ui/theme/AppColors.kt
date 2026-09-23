package com.example.persistencia.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta de colores reutilizable de la nueva interfaz de "Mis tareas".
 * Basada en el mockup mockup_mis_tareas.html.
 * No reemplaza el theme.xml existente: se usa directamente en los
 * composables nuevos (colors.xml original queda intacto, sin usar).
 */
object AppColors {
    val Background = Color(0xFFEFEDE6)   // fondo general (beige claro)
    val Paper = Color(0xFFFBFAF7)        // fondo de la "pantalla" / contenedor
    val CardWhite = Color(0xFFFFFFFF)    // tarjetas de tarea

    val Ink = Color(0xFF1E2420)          // texto principal
    val InkSoft = Color(0xFF6B7168)      // texto secundario
    val InkFaint = Color(0xFFA6ABA0)     // texto tenue (días de otro mes, etc.)

    val Line = Color(0xFFE7E4DA)         // bordes sutiles

    val Accent = Color(0xFF2F6F5E)       // verde principal
    val AccentSoft = Color(0xFFE3EEE9)   // verde muy claro / translúcido
    val AccentStrong = Color(0xFF1F4F42) // verde oscuro (día seleccionado, FAB)

    val Danger = Color(0xFFB5533C)       // acción destructiva (eliminar)
}
