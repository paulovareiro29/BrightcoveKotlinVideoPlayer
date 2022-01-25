package com.example.brightcove.kotlin.videoplayer.data.model

data class PlayerPlaylist(
    val accountId: String,
    val policyKey: String,
    val catalogAssetList: List<CatalogAsset>
)