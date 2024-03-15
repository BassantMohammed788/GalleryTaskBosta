package com.example.gallerytaskboosta.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerytaskboosta.databinding.AlbumItemBinding
import com.example.gallerytaskboosta.models.Album

class MyDiffUtil : DiffUtil.ItemCallback<Album>() {

    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}
class ProfileAdapter(private val listener: ((Album) -> Unit)): ListAdapter<Album, ProfileAdapter.MyViewHolder>(MyDiffUtil()) {
    lateinit var context: Context

    inner class MyViewHolder(val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        context = parent.context
        val binding = AlbumItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentObject = getItem(position)
       holder.binding.albumName.text=currentObject.title

        holder.binding.albumItem.setOnClickListener {
           listener(currentObject)
        }


    }

}