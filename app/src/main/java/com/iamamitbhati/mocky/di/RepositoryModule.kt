package com.iamamitbhati.mocky.di

import com.iamamitbhati.mocky.repository.ProductRepository
import com.iamamitbhati.mocky.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesRepositoryModule(productRepositoryImpl : ProductRepositoryImpl) : ProductRepository
}