package com.example.gallerytaskboosta.ui.profile.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerytaskboosta.adapters.ProfileAdapter
import com.example.gallerytaskboosta.databinding.FragmentProfileBinding
import com.example.gallerytaskboosta.models.Album
import com.example.gallerytaskboosta.models.User
import com.example.gallerytaskboosta.network.ApiState
import com.example.gallerytaskboosta.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var profileAdapter: ProfileAdapter
    private var id = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = Random.nextInt(0, 11)
        setUpRecycler()
        lifecycleScope.launch {
            viewModel.getUser(id.toString())
            viewModel.user.collect { result ->
                when (result) {
                    is ApiState.Loading -> {
                        binding.UserDataContainer.visibility = View.GONE
                    }

                    is ApiState.Failure -> {
                        binding.UserDataContainer.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error ${result.msg}", Toast.LENGTH_LONG)
                            .show()

                    }

                    is ApiState.Success<*> -> {
                        binding.UserDataContainer.visibility = View.VISIBLE
                        binding.userNameTxtView.text =
                            "Name: " + ((result.data as List<User>)[0]).name
                        val address = result.data[0].address
                        binding.addressTxtView.text =
                            "Address: ${address?.suite} ${address?.city} ${address?.street} "
                    }

                }
            }
        }
        lifecycleScope.launch {
            viewModel.getAllAlbums(id.toString())
            viewModel.albums.collect { result ->
                when (result) {
                    is ApiState.Loading -> {
                        binding.albumRecycler.visibility = View.GONE
                        binding.UserDataContainer.visibility = View.GONE
                        binding.albumProgressBar.visibility = View.VISIBLE
                    }

                    is ApiState.Failure -> {
                        binding.albumRecycler.visibility = View.GONE
                        binding.UserDataContainer.visibility = View.GONE
                        binding.albumProgressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error ${result.msg}", Toast.LENGTH_LONG)
                            .show()
                    }

                    is ApiState.Success<*> -> {
                        binding.albumRecycler.visibility = View.VISIBLE
                        binding.UserDataContainer.visibility = View.VISIBLE
                        binding.albumProgressBar.visibility = View.GONE
                        profileAdapter.submitList(result.data as List<Album>)

                    }

                }
            }
        }


    }

    private fun setUpRecycler() {
        profileAdapter = ProfileAdapter(albumListener)
        binding.albumRecycler.apply {
            adapter = profileAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private val albumListener = { album: Album ->
        val action = ProfileFragmentDirections.actionProfileFragmentToAlbumPhotosFragment(album.id.toString())
        findNavController().navigate(action)


    }


}