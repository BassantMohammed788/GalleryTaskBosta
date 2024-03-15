package com.example.gallerytaskboosta.models

import java.io.Serializable

data class Photo(
    val albumId: Int?,
    val id: Int?,
    val thumbnailUrl: String?,
    val title: String?,
    val url: String?
):Serializable