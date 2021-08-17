package com.changers.gallery.model

sealed class GalleryState<out T : Any> {
    object Loading: GalleryState<Nothing>()
    data class Success<out T : Any>(val results: T): GalleryState<T>()
    data class Error(val exception: Throwable): GalleryState<Nothing>()
}