package com.example.todo

import android.app.Application
import android.content.Context
import com.example.data.model.SelectedDay
import com.example.todo.model.database.TasksDataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDo:Application() {

    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()

       context = applicationContext

        SelectedDay
      //  TasksDataBase.init(context)

    }

    companion object {
        var prefs: Prefs? = null
        lateinit var instance: ToDo
            private set
    }
}
