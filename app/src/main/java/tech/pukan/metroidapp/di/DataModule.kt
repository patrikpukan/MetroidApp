package tech.pukan.metroidapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.pukan.metroidapp.data.repository.TransportRepository
import tech.pukan.metroidapp.data.repository.TransportRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    
    @Binds
    abstract fun bindTransportRepository(
        transportRepositoryImpl: TransportRepositoryImpl
    ): TransportRepository
} 