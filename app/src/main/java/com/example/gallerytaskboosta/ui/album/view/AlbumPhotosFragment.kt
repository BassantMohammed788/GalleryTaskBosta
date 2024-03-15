package com.example.gallerytaskboosta.ui.album.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerytaskboosta.adapters.AllbumAdapter
import com.example.gallerytaskboosta.databinding.FragmentAlbumPhotosBinding
import com.example.gallerytaskboosta.models.Photo
import com.example.gallerytaskboosta.network.ApiState
import com.example.gallerytaskboosta.ui.album.viewmodel.AlbumPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AlbumPhotosFragment : Fragment() {
    private lateinit var binding: FragmentAlbumPhotosBinding
    private val viewModel: AlbumPhotosViewModel by viewModels()
    private lateinit var albumAdapter: AllbumAdapter

    private val args:AlbumPhotosFragmentArgs  by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val id = args.albumId
        setUpRecycler()

        lifecycleScope.launch {
            viewModel.getAlbumPhotos(id)
            viewModel.albums.collect { result ->
                when (result) {
                    is ApiState.Loading -> {
                        binding.albumPhotosRecyclerView.visibility = View.GONE
                        binding.searchBar.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ApiState.Failure -> {
                        binding.albumPhotosRecyclerView.visibility = View.GONE
                        binding.searchBar.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error ${result.msg}", Toast.LENGTH_LONG)
                            .show()
                    }

                    is ApiState.Success<*> -> {
                        binding.albumPhotosRecyclerView.visibility = View.VISIBLE
                        binding.searchBar.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        albumAdapter.submitList(result.data as List<Photo>)

                    }

                }
            }
        }


    }

    private fun setUpRecycler() {
        albumAdapter = AllbumAdapter(albumListener)
        binding.albumPhotosRecyclerView.apply {
            adapter = albumAdapter
            layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)
        }
    }

    private val albumListener = { photo: Photo ->
        val action = AlbumPhotosFragmentDirections.actionAlbumPhotosFragmentToPhotoFragment(photo.id.toString(),photo.thumbnailUrl!!)
        findNavController().navigate(action)

    }


}