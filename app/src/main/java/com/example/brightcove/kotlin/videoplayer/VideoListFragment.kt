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
import com.example.brightcove.kotlin.videoplayer.viewmodels.PlayerListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_item_list.view.*

@AndroidEntryPoint
class VideoListFragment : Fragment() {
    companion object {
        val TAG = VideoListFragment::class.java.simpleName
    }

    private val playerListViewModel by activityViewModels<PlayerListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "VideoListFragment onCreate: $this")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        context?.let {
            val videoListAdapter = VideoListViewAdapter(playerListViewModel, viewLifecycleOwner)
            playerListViewModel.isLoading.observe(viewLifecycleOwner, Observer {
                view.spinner.visibility = when (it) {
                    true -> View.VISIBLE
                    else -> View.GONE
                }
            })

            with(view.recyclerViewList) {
                layoutManager = LinearLayoutManager(it)
                adapter = videoListAdapter
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "VideoListFragment onDestroy: $this")
    }
}