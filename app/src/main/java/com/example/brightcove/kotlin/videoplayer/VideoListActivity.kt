package com.example.brightcove.kotlin.videoplayer

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.brightcove.player.model.Video
import com.example.brightcove.kotlin.videoplayer.viewmodels.PlayerListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoListActivity : AppCompatActivity(), VideoListActions {
    private val viewModel by viewModels<PlayerListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_list)

        /*val toolbar: Toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar

        setSupportActionBar(toolbar);*/

        setupFragment()

        viewModel.videoToLoad.observe(this, EventObserver { openVideoPlayer(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, VideoListFragment())
            .commitNow()
    }

    override fun openVideoPlayer(video: Video) {
        val intent = Intent(this, VideoPlayerActivity::class.java)
        intent.putExtra(VideoPlayerActivity.VIDEO_INTENT_DATA, video as Parcelable)
        startActivity(intent)
    }
}

interface VideoListActions {
    fun openVideoPlayer(video: Video)
}