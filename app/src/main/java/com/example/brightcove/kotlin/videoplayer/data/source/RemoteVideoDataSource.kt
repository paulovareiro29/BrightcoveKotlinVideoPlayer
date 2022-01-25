package com.example.brightcove.kotlin.videoplayer.data.source

import com.brightcove.player.edge.Catalog
import com.brightcove.player.edge.VideoListener
import com.brightcove.player.model.Video
import com.example.brightcove.kotlin.videoplayer.data.model.CatalogAsset
import com.example.brightcove.kotlin.videoplayer.data.model.PlayerPlaylist
import com.example.brightcove.kotlin.videoplayer.utils.title
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteVideoDataSource(private val playlist: PlayerPlaylist, private val catalog: Catalog) :
    VideoDataSource {
    override suspend fun getAllVideos(): List<Video> {

        val videoPlaylist = mutableListOf<Video>()
        for (catalogAsset: CatalogAsset in playlist.catalogAssetList) {
            videoPlaylist.add(getCatalogVideo(catalogAsset))
        }
        return videoPlaylist
    }

    @ExperimentalCoroutinesApi
    override fun getVideos(): Flow<Video> = callbackFlow {
        for (catalogAsset: CatalogAsset in playlist.catalogAssetList) {
            catalog.findVideoByID(catalogAsset.id, object : VideoListener() {
                override fun onVideo(video: Video) {
                    video.title = catalogAsset.title
                    offer(video)
                }
            })
        }
        awaitClose {}
    }

    private suspend fun getCatalogVideo(catalogAsset: CatalogAsset) =
        suspendCoroutine<Video> { continuation ->
            catalog.findVideoByID(catalogAsset.id, object : VideoListener() {
                override fun onVideo(video: Video) {
                    video.title = catalogAsset.title
                    continuation.resume(video)
                }
            })
        }

}