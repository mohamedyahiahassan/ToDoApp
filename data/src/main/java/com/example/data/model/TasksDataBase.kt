package com.example.todo.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class TasksDataBase:RoomDatabase() {

    abstract fun tasksDao(): TaskDao


    companion object {

        private var instance: TasksDataBase? = null


        fun init(context: Context): TasksDataBase {

            if (instance == null) {


                instance = Room.databaseBuilder(
                    context,
                    TasksDataBase::class.java,
                    "taskDB"
                ) // taskDB = name of database
                    .fallbackToDestructiveMigration() // delete previous database when version is upgraded. another solution is to provide migration.
                    .build()


            }


            return instance!!
        }

        fun getInstance(): TasksDataBase {

            return instance!!
        }
    }
}