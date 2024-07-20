package com.example.basictodoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert


@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task:Task)

    @Update
    suspend fun updateTask(task:Task)

    @Delete
    suspend fun deleteTask(task:Task)

    @Query("Select * from task")
     fun  getAllTask():LiveData<List<Task>>
}