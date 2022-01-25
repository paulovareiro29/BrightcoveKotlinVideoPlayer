package com.example.brightcove.kotlin.videoplayer.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.brightcove.player.model.Video
import com.example.brightcove.kotlin.videoplayer.Event
import com.example.brightcove.kotlin.videoplayer.data.source.VideoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class PlayerListViewModel @ViewModelInject constructor(val videoRepository: VideoRepository) :
    ViewModel() {

    private val _videoList = MutableLiveData<List<Video>>()

    @Suppress("unused")
    val videoList: LiveData<List<Video>> = _videoList

    @ExperimentalCoroutinesApi
    val videoFlow: LiveData<Video> = videoRepository.getVideos().asLiveData()

    private val _videoToLoad = MutableLiveData<Event<Video>>()
    val videoToLoad: LiveData<Event<Video>> = _videoToLoad

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadVideos(forceUpdate: Boolean) {
        _isLoading.value = true
        val mustUpdate = forceUpdate || _videoList.value?.isEmpty() ?: true
        if (mustUpdate) {
            viewModelScope.launch {
                _videoList.postValue(videoRepository.getAllVideos())
                _isLoading.value = false
            }
        }

    }

    fun openVideo(video: Video) {
        // There's a problem with some properties that are not Parcelable/Serializable, so we'll remove them
        removeNonSerializableVideoProperties(video)
        _videoToLoad.value = Event(video)
    }

    private fun removeNonSerializableVideoProperties(video: Video) {
        //Caption Sources use android.util.Pair<> which are not serializable
        video.properties.remove(Video.Fields.CAPTION_SOURCES)

        //PreviewThumbnail sources have a class that is not yet serializable as of Brightcove SDK version 6.14.0
        video.properties.remove(Video.Fields.PREVIEW_THUMBNAIL_SOURCES)
    }
}