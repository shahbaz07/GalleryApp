package com.changers.gallery.usecase

import com.changers.gallery.model.Breed
import com.changers.gallery.model.BreedImage
import kotlinx.coroutines.flow.Flow

interface GalleryUseCase {

    suspend fun fetchBreeds(): Flow<List<Breed>>

    suspend fun fetchBreedImages(id: String): Flow<List<BreedImage>>
}