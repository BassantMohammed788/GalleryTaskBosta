package com.example.gallerytaskboosta.repository

import com.example.gallerytaskboosta.models.Album
import com.example.gallerytaskboosta.models.PhotosResponse
import com.example.gallerytaskboosta.models.User
import com.example.gallerytaskboosta.network.GalleryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val galleryApi: GalleryApi):Repository {
    override suspend fun getAllAlbums(userId: String): Flow<List<Album>> = flowOf(galleryApi.getAlbums(userId))
    override suspend fun getAlbumPhotos(albumId: String):Flow<PhotosResponse> = flowOf(galleryApi.getPhotos(albumId))
    override suspend fun getUser(userId:String): Flow<List<User>> = flowOf(galleryApi.getUser(userId))
}