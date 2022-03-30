package com.example.brightcove.kotlin.videoplayer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.brightcove.player.edge.Catalog
import com.brightcove.player.edge.VideoListener
import com.brightcove.player.model.Video
import com.brightcove.player.view.BrightcoveExoPlayerVideoView
import com.brightcove.player.view.BrightcovePlayer
import com.example.brightcove.kotlin.videoplayer.utils.title

class VideoPlayerActivity : BrightcovePlayer() {

    companion object {
        const val VIDEO_INTENT_DATA = "video_intent_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // When extending the BrightcovePlayer, we must assign the brightcoveVideoView before
        // entering the superclass. This allows for some stock video player lifecycle
        // management.  Establish the video object and use it's event emitter to get important
        // notifications and to control logging.
        setContentView(R.layout.activity_main)
        brightcoveVideoView =
            findViewById<BrightcoveExoPlayerVideoView>(R.id.brightcove_video_view)

        super.onCreate(savedInstanceState)

        val video: Video? = intent?.getParcelableExtra(VIDEO_INTENT_DATA)
        video?.let { addAndStart(it) } ?: loadDefaultVideo()

    }

    private fun loadDefaultVideo() {
        // Get the event emitter from the SDK and create a catalog request to fetch a video from the
        // Brightcove Edge service, given a video id, an account id and a policy key.
        // Get the event emitter from the SDK and create a catalog request to fetch a video from the
        // Brightcove Edge service, given a video id, an account id and a policy key.
        val eventEmitter = brightcoveVideoView.eventEmitter
        val catalog = Catalog.Builder(eventEmitter, getString(R.string.account))
            .setPolicy(getString(R.string.policy))
            .build()

        catalog.findVideoByID(getString(R.string.videoId), object : VideoListener() {
            // Add the video found to the queue with add().
            // Start playback of the video with start().
            override fun onVideo(video: Video) {
                Log.v(TAG, "onVideo: video = $video")
                addAndStart(video)
            }
        })
    }

    private fun addAndStart(video: Video) {
        brightcoveVideoView.add(video)
        findViewById<TextView>(R.id.video_player_title).text = video.title
        findViewById<TextView>(R.id.video_player_description).text = video.description

        brightcoveVideoView.start()
    }

    fun goback(v: View?){
        finish()
    }
}
