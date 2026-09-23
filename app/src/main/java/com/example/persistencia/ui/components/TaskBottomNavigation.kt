package com.example.persistencia.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.persistencia.ui.theme.AppColors

/** Pestaña seleccionada: 0 = Buscar por fecha, 1 = Todas las tareas. */
@Composable
fun TaskBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(containerColor = AppColors.Paper) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = { Icon(Icons.Outlined.Search, contentDescription = "Buscar") },
            label = { Text("Buscar") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AppColors.AccentStrong,
                selectedTextColor = AppColors.AccentStrong,
                unselectedIconColor = AppColors.InkFaint,
                unselectedTextColor = AppColors.InkFaint,
                indicatorColor = AppColors.AccentSoft
            )
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = { Icon(Icons.AutoMirrored.Outlined.List, contentDescription = "Todas las tareas") },
            label = { Text("Todas las tareas") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AppColors.AccentStrong,
                selectedTextColor = AppColors.AccentStrong,
                unselectedIconColor = AppColors.InkFaint,
                unselectedTextColor = AppColors.InkFaint,
                indicatorColor = AppColors.AccentSoft
            )
        )
    }
}
