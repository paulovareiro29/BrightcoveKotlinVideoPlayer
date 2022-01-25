package com.example.brightcove.kotlin.videoplayer.di

import android.content.Context
import com.brightcove.player.edge.Catalog
import com.brightcove.player.event.EventEmitter
import com.brightcove.player.event.EventEmitterImpl
import com.example.brightcove.kotlin.videoplayer.data.model.PlayerPlaylist
import com.example.brightcove.kotlin.videoplayer.data.source.RemoteVideoDataSource
import com.example.brightcove.kotlin.videoplayer.data.source.VideoDataSource
import com.example.brightcove.kotlin.videoplayer.data.source.VideoRepository
import com.example.brightcove.kotlin.videoplayer.utils.IOUtil
import com.example.brightcove.kotlin.videoplayer.utils.PlaylistParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {
    const val DEFAULT_PLAYER_PLAYLIST_FILE_PATH = "player_plalist.json"

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteVideoDataSource

    @Singleton
    @RemoteVideoDataSource
    @Provides
    fun provideRemoteVideoDataSource(playlist: PlayerPlaylist, catalog: Catalog): VideoDataSource {
        return RemoteVideoDataSource(playlist, catalog)
    }

    @Singleton
    @Provides
    fun provideEventEmitter(): EventEmitter {
        return EventEmitterImpl()
    }

    @Singleton
    @Provides
    fun provideCatalog(eventEmitter: EventEmitter, playlist: PlayerPlaylist): Catalog {
        return Catalog.Builder(eventEmitter, playlist.accountId).setPolicy(playlist.policyKey)
            .build()
    }

    @Singleton
    @Provides
    fun providesPlaylist(@ApplicationContext context: Context): PlayerPlaylist {
        val playlistString =
            IOUtil.readStringFromAssets(context, DEFAULT_PLAYER_PLAYLIST_FILE_PATH)
        return PlaylistParser.parsePlaylist(playlistString)
    }
}

@InstallIn(ApplicationComponent::class)
@Module
object VideoRepositoryModule {
    @Provides
    @Singleton
    fun provideFunRepository(@AppModule.RemoteVideoDataSource remoteVideoDataSource: VideoDataSource): VideoRepository {
        return VideoRepository(remoteVideoDataSource)
    }
}