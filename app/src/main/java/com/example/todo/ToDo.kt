package com.example.todo

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.model.SelectedDay
import com.example.todo.model.database.TasksDataBase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


@HiltAndroidApp
class ToDo:Application() {



    override fun onCreate() {
        super.onCreate()



         TasksDataBase.init(applicationContext)

        SelectedDay

    }

    /*


     suspend fun changeDark (value:Boolean){

        val darkTheme = booleanPreferencesKey("dark_Mode")

        dataStore.edit {settings->

            settings[darkTheme] = value
        }
    }

     suspend fun readDarkMode ():Boolean?{

        val darkTheme = booleanPreferencesKey("dark_Mode")

        val preferences = dataStore.data.first()

        return preferences[darkTheme]
    }

     */
}
