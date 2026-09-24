package com.example.persistencia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.persistencia.ui.theme.AppColors

/**
 * Pantalla completa para crear/editar una tarea (estilo "bloc de notas").
 * No toca Task/TaskDao/TaskRepository/TaskViewModel: solo llama a
 * onConfirm con los textos finales.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    title: String,
    initialTitulo: String,
    initialDescripcion: String,
    onBack: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var titulo by remember { mutableStateOf(initialTitulo) }
    var descripcion by remember { mutableStateOf(initialDescripcion) }

    Scaffold(
        containerColor = AppColors.Paper,
        topBar = {
            TopAppBar(
                title = { Text(title, color = AppColors.Ink) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = AppColors.Ink)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onConfirm(titulo, descripcion) },
                        enabled = titulo.isNotBlank()
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Guardar",
                            tint = if (titulo.isNotBlank()) AppColors.AccentStrong else AppColors.InkFaint
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColors.Paper)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            TextField(
                value = titulo,
                onValueChange = { titulo = it },
                placeholder = { Text("Título") },
                textStyle = MaterialTheme.typography.headlineSmall,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppColors.Paper,
                    unfocusedContainerColor = AppColors.Paper,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                placeholder = { Text("Escribe algo...") },
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppColors.Paper,
                    unfocusedContainerColor = AppColors.Paper,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}