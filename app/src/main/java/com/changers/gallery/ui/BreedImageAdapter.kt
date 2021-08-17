package com.changers.gallery.ui

import android.content.Context
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.changers.gallery.model.BreedImage
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.changers.gallery.databinding.BreedImageItemBinding

import android.view.LayoutInflater
import android.view.View
import com.changers.gallery.R

class BreedImageAdapter(context: Context, images: List<BreedImage>): ArrayAdapter<BreedImage>(context, 0, images) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: BreedImageViewHolder?
        if (convertView == null) {
            val binding = BreedImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            viewHolder = BreedImageViewHolder(binding)
            binding.root.tag = viewHolder
        } else {
            viewHolder = convertView.tag as BreedImageViewHolder
        }
        getItem(position)?.let {
            Glide.with(viewHolder.binding.root.context).load(it.url).placeholder(R.drawable.placeholder)
            .centerCrop().into(viewHolder.binding.imageView)
        }
        return viewHolder.binding.root
    }
}

class BreedImageViewHolder(val binding: BreedImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(breadImage: BreedImage) {
        Glide.with(binding.root.context).load(breadImage.url)
            .centerCrop().into(binding.imageView)
    }
}