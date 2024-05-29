package com.example.data.di

import com.example.data.repository.RepositoryImpl
import com.example.domain.contract.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepositoryImpl(impl: RepositoryImpl):DatabaseRepository{

        return impl
    }
}