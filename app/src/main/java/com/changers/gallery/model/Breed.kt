package com.changers.gallery.model

data class Breed(val id: String, val name: String) {

    override fun toString(): String {
        return name
    }
}