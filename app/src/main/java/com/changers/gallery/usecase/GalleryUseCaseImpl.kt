package com.changers.gallery.usecase

import com.changers.gallery.model.Breed
import com.changers.gallery.model.BreedImage
import com.changers.gallery.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryUseCaseImpl @Inject constructor(private val repository: GalleryRepository) : GalleryUseCase {

    override suspend fun fetchBreeds(): Flow<List<Breed>> {
        return repository.fetchBreeds()
    }

    override suspend fun fetchBreedImages(id: String): Flow<List<BreedImage>> {
        return repository.fetchBreedImage(id)
    }
}