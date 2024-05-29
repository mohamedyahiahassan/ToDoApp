package com.example.todo.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("select * from Tasks")
    suspend fun getAllTasks():List<Task>

    @Query("select * from Tasks where id = :id")
    suspend fun getTaskById(id:Int):Task

}