package com.changers.gallery.repository

import com.changers.gallery.model.Breed
import com.changers.gallery.model.BreedImage
import com.changers.gallery.service.GalleryService
import com.changers.gallery.repository.exception.NoDataException
import com.changers.gallery.repository.exception.ServerException
import com.changers.gallery.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val service: GalleryService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GalleryRepository {

    override suspend fun fetchBreeds(): Flow<List<Breed>> {
        return flow {
            // Fetching data from API
            val response = safeApiCall {
                service.fetchBreeds()
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                } ?: kotlin.run {
                    throw NoDataException()
                }
            } else {
                throw ServerException()
            }
        }
        .flowOn(dispatcher)
    }

    override suspend fun fetchBreedImage(id: String): Flow<List<BreedImage>> {
        return flow {
            // Fetching data from API
            val response = safeApiCall {
                service.fetchBreedImages(id)
            }
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                } ?: kotlin.run {
                    throw NoDataException()
                }
            } else {
                throw ServerException()
            }
        }
            .flowOn(dispatcher)
    }
}