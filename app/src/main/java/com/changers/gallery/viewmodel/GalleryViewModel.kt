package com.changers.gallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.changers.gallery.model.Breed
import com.changers.gallery.model.BreedImage
import com.changers.gallery.model.GalleryState
import com.changers.gallery.usecase.GalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val useCase: GalleryUseCase) : ViewModel() {

    // Backing property to avoid state updates from other classes
    private val _breeds = MutableStateFlow<GalleryState<List<Breed>>>(GalleryState.Loading)
    // The UI collects from this StateFlow to get its state updates
    val breeds: StateFlow<GalleryState<List<Breed>>> = _breeds

    private val _breedImages = MutableStateFlow<GalleryState<List<BreedImage>>>(GalleryState.Loading)
    // The UI collects from this StateFlow to get its state updates
    val breedImages: StateFlow<GalleryState<List<BreedImage>>> = _breedImages

    fun fetchBreeds() {
        viewModelScope.launch {
            useCase.fetchBreeds()
                .catch { exception ->
                    _breeds.value = GalleryState.Error(exception)
                }
                .collect {
                    _breeds.value = GalleryState.Success(it)
                }
        }
    }

    fun fetchBreedImages(id: String) {
        if (id.isEmpty()) {
            return
        }
        viewModelScope.launch {
            useCase.fetchBreedImages(id)
                .catch { exception ->
                    _breedImages.value = GalleryState.Error(exception)
                }
                .collect {
                    _breedImages.value = GalleryState.Success(it)
                }
        }
    }
}