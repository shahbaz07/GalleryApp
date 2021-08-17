package com.changers.gallery.di

import com.changers.gallery.repository.GalleryRepository
import com.changers.gallery.repository.GalleryRepositoryImpl
import com.changers.gallery.usecase.GalleryUseCase
import com.changers.gallery.usecase.GalleryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GalleryModule {

    @Binds
    abstract fun bindSoccerResultsRepository(repository: GalleryRepositoryImpl) : GalleryRepository

    @Binds
    abstract fun bindSoccerResultsUseCase(useCase: GalleryUseCaseImpl) : GalleryUseCase
}