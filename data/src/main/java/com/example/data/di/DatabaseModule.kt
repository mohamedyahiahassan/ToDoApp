package com.example.data.di

import com.example.todo.model.database.TasksDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    fun provideInstance(): TasksDataBase {

        return TasksDataBase.getInstance()
    }
}
