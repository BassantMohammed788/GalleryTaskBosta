package com.example.gallerytaskboosta.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallerytaskboosta.R
import com.example.gallerytaskboosta.databinding.AlbumPhotoItemBinding
import com.example.gallerytaskboosta.models.Photo


private class AlbumDiffUtil : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}
class AllbumAdapter(private val listener: ((Photo) -> Unit)): ListAdapter<Photo, AllbumAdapter.MyViewHolder>(AlbumDiffUtil()) {
    lateinit var context: Context

    inner class MyViewHolder(val binding: AlbumPhotoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        context = parent.context
        val binding = AlbumPhotoItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentObject = getItem(position)
        Glide.with(context)
            .load(currentObject?.thumbnailUrl)
            .error(R.drawable.image_error)
            .placeholder(R.drawable.loading_img)
            .into(holder.binding.imageViewGalleryItem)

        holder.binding.imageViewGalleryItem.setOnClickListener {
            listener(currentObject)
        }


    }

}