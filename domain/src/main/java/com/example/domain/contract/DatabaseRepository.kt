package com.example.domain.contract

import com.example.todo.model.database.Task
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun getAllTasks(): Flow<MutableList<Task>>

    suspend fun getTaskById(id:Int):Task
}