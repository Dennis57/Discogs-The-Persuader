package com.example.thepersuader.releaseDetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thepersuader.R
import com.example.thepersuader.ViewModelFactory
import com.example.thepersuader.adapter.VideoAdapter
import com.example.thepersuader.databinding.FragmentTrackListBinding
import com.example.thepersuader.databinding.FragmentVideoBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class VideoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by activityViewModels<ReleaseDetailViewModel> { viewModelFactory }

    private lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Data Binding
        binding = FragmentVideoBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment
        binding.rvVideos.layoutManager = LinearLayoutManager(this.context)

        val videoAdapter = VideoAdapter()

        binding.rvVideos.adapter = videoAdapter

        viewModel.releaseDetails.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                videoAdapter.submitList(it.videos.toMutableList())
            }
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object{
        fun newInstance(release_id: String): VideoFragment {
            val videoFragment = VideoFragment()

            var args = Bundle()

            args.putString("release_id", release_id)
            videoFragment.arguments = args
            return videoFragment
        }
    }
}