package com.example.persistencia.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.persistencia.data.Task
import com.example.persistencia.data.TaskDatabase
import com.example.persistencia.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val tasks: StateFlow<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        tasks = repository.allTasks.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun addTask(titulo: String, descripcion: String) {
        if (titulo.isBlank()) return
        viewModelScope.launch {
            repository.insert(
                Task(
                    titulo = titulo,
                    descripcion = descripcion,
                    isSynced = false
                )
            )
        }
    }

    fun toggleTaskState(task: Task) {
        viewModelScope.launch {
            repository.update(
                task.copy(
                    estadoCompletado = !task.estadoCompletado,
                    isSynced = false
                )
            )
        }
    }

    fun updateTask(task: Task, nuevoTitulo: String, nuevaDescripcion: String) {
        if (nuevoTitulo.isBlank()) return
        viewModelScope.launch {
            repository.update(
                task.copy(
                    titulo = nuevoTitulo,
                    descripcion = nuevaDescripcion,
                    isSynced = false
                )
            )
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }

    fun syncTasks() {
        viewModelScope.launch {
            val pendientes = repository.getUnsyncedTasks()
            pendientes.forEach { task ->
                // Simula el envío a red y marca como sincronizado localmente
                repository.update(task.copy(isSynced = true))
            }
        }
    }
}