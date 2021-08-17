package com.changers.gallery.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.changers.gallery.databinding.GalleryFragmentBinding
import com.changers.gallery.model.Breed
import com.changers.gallery.model.GalleryState
import com.changers.gallery.util.NetworkState
import com.changers.gallery.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private lateinit var binding: GalleryFragmentBinding

    private val viewModel: GalleryViewModel by viewModels()

    private lateinit var adapter: BreedImageAdapter

    private lateinit var networkState: NetworkState

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = GalleryFragmentBinding.inflate(inflater)
        context?.let {
            networkState = NetworkState(it)
        }
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkState.observe(viewLifecycleOwner) {
            if (it) {
                binding.offline.visibility = View.GONE
                binding.loading.visibility = View.VISIBLE
                viewModel.fetchBreeds()
            } else {
                binding.offline.visibility = View.VISIBLE
            }
        }
        observeBreeds()
        observeBreedImages()
        viewModel.fetchBreeds()
    }

    private fun observeBreeds() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.breeds.collect { breeds ->
                    when (breeds) {
                        is GalleryState.Success -> {
                            binding.loading.visibility = View.GONE
                            val breedsWithDefaultValue = ArrayList<Breed>(breeds.results.size + 1)
                            breedsWithDefaultValue.add(Breed("", "Please select..."))
                            breedsWithDefaultValue.addAll(breeds.results)
                            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, breedsWithDefaultValue)
                            binding.spinner.adapter = adapter
                        }
                        is GalleryState.Error -> binding.loading.visibility = View.GONE
                        is GalleryState.Loading -> binding.loading.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun observeBreedImages() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.breedImages.collect { breedImages ->
                    when (breedImages) {
                        is GalleryState.Success -> {
                            binding.loading.visibility = View.GONE
                            binding.imagesGridView.adapter = BreedImageAdapter(requireContext(), breedImages.results)
                        }
                        is GalleryState.Error -> binding.loading.visibility = View.GONE
                        is GalleryState.Loading -> binding.loading.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun initViews() {
        initSpinner()
    }

    private fun initSpinner() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // You can define you actions as you want
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                val breed = binding.spinner.selectedItem as Breed
                viewModel.fetchBreedImages(breed.id)
            }
        }
    }
}