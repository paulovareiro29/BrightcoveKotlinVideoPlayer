package com.example.brightcove.kotlin.videoplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.brightcove.player.model.Video
import com.brightcove.player.util.StringUtil
import com.bumptech.glide.Glide
import com.example.brightcove.kotlin.videoplayer.utils.title
import com.example.brightcove.kotlin.videoplayer.viewmodels.PlayerListViewModel
import org.w3c.dom.Text

class VideoListViewAdapter(
    private val playerListViewModel: PlayerListViewModel,
    lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<VideoListViewAdapter.ViewHolder>() {

    private val videoList = mutableListOf<Video>()

    init {
        @Suppress("EXPERIMENTAL_API_USAGE")
        playerListViewModel.videoFlow.observe(lifecycleOwner, Observer { addVideo(it) })
    }

    private fun addVideo(video: Video) {
        videoList.add(video)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoList[position]
        holder.videoNameView.text = video.title
        holder.videoDescriptionView.text = video.description
        holder.videoDurationView.text = StringUtil.stringForTime(video.duration.toLong())
        holder.videoThumbnailImageView.setOnClickListener { playerListViewModel.openVideo(video) }
        val poster = video.posterImage
        Glide.with(holder.videoThumbnailImageView)
            .load(poster.toString())
            .centerCrop()
            .placeholder(R.drawable.ic_play)
            .into(holder.videoThumbnailImageView)
    }

    override fun getItemCount(): Int = videoList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoNameView: TextView = view.findViewById(R.id.video_name)
        val videoDescriptionView : TextView = view.findViewById(R.id.video_description)
        val videoDurationView: TextView = view.findViewById(R.id.video_duration)
        val videoThumbnailImageView: ImageView = view.findViewById(R.id.video_thumbnail)
        val videoCategoryView: TextView = view.findViewById(R.id.video_category)
    }
}