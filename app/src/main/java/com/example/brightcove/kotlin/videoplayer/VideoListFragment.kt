package com.example.brightcove.kotlin.videoplayer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brightcove.kotlin.videoplayer.data.model.CatalogAsset
import com.example.brightcove.kotlin.videoplayer.viewmodels.PlayerListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_item_list.view.*

@AndroidEntryPoint
class VideoListFragment : Fragment() {
    companion object {
        val TAG = VideoListFragment::class.java.simpleName
    }

    private val playerListViewModel by activityViewModels<PlayerListViewModel>()
    private var showingPlaylist: Boolean = true
    private var currentPlaylist: List<CatalogAsset> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "VideoListFragment onCreate: $this")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)



        loadPlaylist(null)

        for(v in currentPlaylist){
            Log.d("VIDEOO",v.id)
        }

        context?.let {
            val videoListAdapter = VideoListViewAdapter(playerListViewModel, viewLifecycleOwner, currentPlaylist, showingPlaylist)
            playerListViewModel.isLoading.observe(viewLifecycleOwner, Observer {
                view.spinner.visibility = when (it) {
                    true -> View.VISIBLE
                    else -> View.GONE
                }
            })


            playerListViewModel.playlistToLoad.observe(viewLifecycleOwner, Observer {
                showingPlaylist = false
                loadPlaylist(it)
                playerListViewModel.videoList
                view.recyclerViewList.adapter = VideoListViewAdapter(playerListViewModel, viewLifecycleOwner, currentPlaylist, showingPlaylist)

            })

            with(view.recyclerViewList) {
                layoutManager = LinearLayoutManager(it)
                adapter = videoListAdapter
            }

        }



        return view
    }


    fun loadPlaylist(playlist: String?) {
        if(!showingPlaylist && playlist == null) return

        var list: List<CatalogAsset> = playerListViewModel.playlist.catalogAssetList
        var availableSongs: MutableList<String> = mutableListOf()

        var newList = mutableListOf<CatalogAsset>()

        for(v in list){
            if(v.song == null) continue

            if(showingPlaylist){
                var found = false
                for(s in availableSongs){
                    if(v.song == s) found = true
                }

                if(!found) availableSongs.add(availableSongs.size, v.song)
            }else{
                if(v.song == playlist){
                    newList.add(newList.size,v)
                }
            }

        }



        if(showingPlaylist){
            for(s in availableSongs){
                for(v in list){
                    if(v.song == null) continue

                    if(v.song == s) {


                        newList.add(newList.size,v)
                        break
                    }
                }
            }
        }

        currentPlaylist = newList
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "VideoListFragment onDestroy: $this")
    }
}