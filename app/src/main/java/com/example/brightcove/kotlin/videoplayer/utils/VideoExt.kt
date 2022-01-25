package com.example.brightcove.kotlin.videoplayer.utils

import com.brightcove.player.model.Video

const val VIDEO_CATALOG_ASSET_TITLE = "videoTitle";
var Video.title: String
    get() {
        return getStringProperty(VIDEO_CATALOG_ASSET_TITLE) ?: ""
    }
    set(value) {
        properties[VIDEO_CATALOG_ASSET_TITLE] = value
    }


