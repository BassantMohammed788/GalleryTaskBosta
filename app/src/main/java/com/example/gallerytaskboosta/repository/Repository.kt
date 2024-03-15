package com.example.gallerytaskboosta.repository

import com.example.gallerytaskboosta.models.Album
import com.example.gallerytaskboosta.models.PhotosResponse
import com.example.gallerytaskboosta.models.User
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getAllAlbums(userId:String):  Flow<List<Album>>
    suspend fun getAlbumPhotos(albumId:String):Flow<PhotosResponse>
    suspend fun getUser(userId:String):Flow<List<User>>

}