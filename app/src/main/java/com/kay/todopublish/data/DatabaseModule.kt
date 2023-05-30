package com.kay.todopublish.data

import android.content.Context
import androidx.room.Room
import com.kay.todopublish.data.repository.DataStoreRepository
import com.kay.todopublish.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Singleton means the app will only one instance of a class.

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ToDoDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: ToDoDatabase) = database.toDoDao()

    @Singleton
    @Provides
    fun provideDatastore(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)
}
