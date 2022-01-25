package com.example.brightcove.kotlin.videoplayer.data.source

import com.brightcove.player.model.Video
import kotlinx.coroutines.flow.Flow

interface VideoDataSource {
    suspend fun getAllVideos(): List<Video>
    fun getVideos(): Flow<Video>
}