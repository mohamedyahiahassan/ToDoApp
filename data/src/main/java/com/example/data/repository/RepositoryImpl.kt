package com.example.data.repository

import com.example.domain.contract.DatabaseRepository
import com.example.todo.model.database.Task
import com.example.todo.model.database.TaskDao
import com.example.todo.model.database.TasksDataBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val tasksDataBase: TasksDataBase):DatabaseRepository {


    override suspend fun insertTask(task: Task) {

        tasksDataBase.tasksDao().insertTask(task)

    }

    override suspend fun updateTask(task: Task) {

        tasksDataBase.tasksDao().updateTask(task)
    }

    override suspend fun deleteTask(task: Task) {

        tasksDataBase.tasksDao().deleteTask(task)
    }

    override suspend fun getAllTasks(): Flow<MutableList<Task>>  = flow {

       val listOfTasks =  tasksDataBase.tasksDao().getAllTasks()

        emit(listOfTasks)
    }

    override suspend fun getTaskById(id: Int): Task {

      return  tasksDataBase.tasksDao().getTaskById(id)
    }
}