package com.example.domain.contract

import com.example.todo.model.database.Task

interface DatabaseRepository {

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun getAllTasks():List<Task>

    suspend fun getTaskById(id:Int):Task
}