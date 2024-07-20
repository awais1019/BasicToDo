package com.example.basictodoapp

import androidx.lifecycle.LiveData
import com.example.basictodoapp.db.Task
import com.example.basictodoapp.db.TaskDao

class Repository(private val taskDao: TaskDao) {

    fun getAllTasks():LiveData<List<Task>>
    {
       return taskDao.getAllTask()
    }
    suspend fun upsertTask(task: Task)
    {
        taskDao.upsertTask(task);
    }
   suspend fun deleteTask(task: Task)
    {
        taskDao.deleteTask(task);
    }

    suspend fun updateTask(task:Task)
    {
        taskDao.updateTask(task)
    }

}