package com.example.gallerytaskboosta.network

import com.example.gallerytaskboosta.models.Album
import com.example.gallerytaskboosta.models.PhotosResponse
import com.example.gallerytaskboosta.models.User
import com.example.gallerytaskboosta.utilities.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryApi {


    @GET(Constants.ALBUMS_END_POINT)
    suspend fun getAlbums(@Query("userId") userId: String) : List<Album>

    @GET(Constants.PHOTOS_END_POINT)
    suspend fun getPhotos(@Query("albumId") albumId: String) : PhotosResponse

    @GET(Constants.USER_END_POINT)
    suspend fun getUser(@Query("userId") userId: String):List<User>
}
