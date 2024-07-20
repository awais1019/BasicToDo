package com.example.basictodoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basictodoapp.db.Task
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: Repository) : ViewModel() {


    val allTasks: LiveData<List<Task>> = repository.getAllTasks()

    fun upsertTask(task: Task) {
        viewModelScope.launch {
            repository.upsertTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
