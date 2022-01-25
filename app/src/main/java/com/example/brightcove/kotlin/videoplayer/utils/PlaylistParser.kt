package com.example.brightcove.kotlin.videoplayer.utils

import com.example.brightcove.kotlin.videoplayer.data.model.PlayerPlaylist
import com.google.gson.Gson

object PlaylistParser {
    private val GSON = Gson()

    fun parsePlaylist(playlistJson: String): PlayerPlaylist {
        return GSON.fromJson(playlistJson, PlayerPlaylist::class.java)
    }
}