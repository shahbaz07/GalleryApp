package com.changers.gallery.service

import com.changers.gallery.model.Breed
import com.changers.gallery.model.BreedImage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val DOG_BREEDS: String = "/v1/breeds"
private const val DOG_BREED_IMAGES: String = "/v1/images/search"

interface GalleryService {

    @GET(DOG_BREEDS)
    suspend fun fetchBreeds(): Response<List<Breed>>

    @GET(DOG_BREED_IMAGES)
    suspend fun fetchBreedImages(@Query("breed_ids") id: String,
                                 @Query("limit") limit: Int = 100): Response<List<BreedImage>>
}