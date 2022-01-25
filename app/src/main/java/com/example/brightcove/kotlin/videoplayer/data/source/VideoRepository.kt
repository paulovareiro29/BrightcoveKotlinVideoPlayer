package com.example.brightcove.kotlin.videoplayer.data.source

import kotlinx.coroutines.ExperimentalCoroutinesApi

class VideoRepository(private val remoteVideoDataSource: VideoDataSource) : VideoDataSource {

    override suspend fun getAllVideos() = remoteVideoDataSource.getAllVideos()

    @ExperimentalCoroutinesApi
    override fun getVideos() = remoteVideoDataSource.getVideos()
}