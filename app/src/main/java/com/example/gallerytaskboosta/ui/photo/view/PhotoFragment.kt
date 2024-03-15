package com.example.gallerytaskboosta.ui.photo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gallerytaskboosta.R
import com.example.gallerytaskboosta.databinding.FragmentPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoBinding
    private val args: PhotoFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.photoId
        val url = args.photoUrl

        Glide.with(requireContext())
            .load(url)
            .error(R.drawable.image_error)
            .placeholder(R.drawable.loading_img)
            .into(binding.imageView)
    }
}