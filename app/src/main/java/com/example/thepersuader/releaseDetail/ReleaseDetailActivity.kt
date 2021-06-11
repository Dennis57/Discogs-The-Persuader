package com.example.thepersuader.releaseDetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thepersuader.ViewModelFactory
import com.example.thepersuader.adapter.TracklistAdapter
import com.example.thepersuader.adapter.VideoAdapter
import com.example.thepersuader.databinding.ActivityReleaseDetailBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class ReleaseDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ReleaseDetailViewModel

    private lateinit var binding: ActivityReleaseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        // Initialize Data Binding
        binding = ActivityReleaseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bind to Adapter
//        binding.rvTracklist.layoutManager = LinearLayoutManager(this)
//        binding.rvVideos.layoutManager = LinearLayoutManager(this)

//        val tracklistAdapter = TracklistAdapter()
//        val videoAdapter = VideoAdapter()
//
//        binding.rvTracklist.adapter = tracklistAdapter
//        binding.rvVideos.adapter = videoAdapter

        val release_id = intent.extras?.getInt("id")

        loadFragment(TrackListFragment.newInstance(release_id.toString()))

        binding.btnTracklist.setOnClickListener {
            loadFragment(TrackListFragment.newInstance(release_id.toString()))
        }

        binding.btnVideos.setOnClickListener {
            loadFragment(VideoFragment.newInstance(release_id.toString()))
        }

        viewModel = ViewModelProvider(this, viewModelFactory).get(ReleaseDetailViewModel::class.java)

        viewModel.getReleaseDetails(release_id ?: 0)

        viewModel.releaseDetails.observe(this, Observer {
            if (null != it) {
                binding.tvTitle.text = it.title
                binding.tvReleaseYear.text = it.year.toString()
                binding.tvArtistName.text = it.artists

//                tracklistAdapter.submitList(it.trackList.toMutableList())
//                videoAdapter.submitList(it.videos.toMutableList())
            }
        })

        viewModel.showLoading.observe(this, Observer {
            if (it == true) {
                binding.tvTitle.visibility = View.INVISIBLE
                binding.tvReleaseYear.visibility = View.INVISIBLE
                binding.tvArtistName.visibility = View.INVISIBLE
                binding.prTitle.visibility = View.VISIBLE
            } else {
                binding.tvTitle.visibility = View.VISIBLE
                binding.tvReleaseYear.visibility = View.VISIBLE
                binding.tvArtistName.visibility = View.VISIBLE
                binding.prTitle.visibility = View.GONE
            }
        })

        viewModel.releaseDetailError.observe(this, Observer {
            if (it == true) {
                binding.ivError.visibility = View.VISIBLE
                binding.tvTitle.visibility = View.INVISIBLE
                binding.tvReleaseYear.visibility = View.INVISIBLE
                binding.tvArtistName.visibility = View.INVISIBLE
            }
        })

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.flContainer.id, fragment).commit()
    }
}