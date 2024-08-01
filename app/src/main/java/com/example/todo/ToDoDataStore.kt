package com.example.todo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ToDoDataStore(context: Context) {

   val dataStore = context.dataStore

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


}