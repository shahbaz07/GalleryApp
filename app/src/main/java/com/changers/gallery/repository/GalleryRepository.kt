package com.changers.gallery.repository

import com.changers.gallery.model.Breed
import com.changers.gallery.model.BreedImage
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {

    suspend fun fetchBreeds(): Flow<List<Breed>>

    suspend fun fetchBreedImage(id: String): Flow<List<BreedImage>>
}