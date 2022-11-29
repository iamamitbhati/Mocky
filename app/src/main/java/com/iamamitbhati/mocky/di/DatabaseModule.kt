package com.iamamitbhati.mocky.di

import android.app.Application
import androidx.room.Room
import com.iamamitbhati.mocky.data.local.MockyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): MockyDatabase {
        return Room
            .databaseBuilder(application, MockyDatabase::class.java, "mocky.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProductDao(mockyDatabase: MockyDatabase) = mockyDatabase.productDao()

}