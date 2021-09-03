package com.example.thepersuader.releaseDetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thepersuader.ViewModelFactory
import com.example.thepersuader.adapter.TracklistAdapter
import com.example.thepersuader.databinding.FragmentTrackListBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class TrackListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentTrackListBinding

    private val viewModel by activityViewModels<ReleaseDetailViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Data Binding
        binding = FragmentTrackListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inflate the layout for this fragment
        binding.rvTracklist.layoutManager = LinearLayoutManager(this.context)

        val tracklistAdapter = TracklistAdapter()

        binding.rvTracklist.adapter = tracklistAdapter

        viewModel.getReleaseDetails(1)

        viewModel.releaseDetails.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                tracklistAdapter.submitList(it.trackList.toMutableList())
            }
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object{
        fun newInstance(release_id: String): TrackListFragment {
            val trackListFragment = TrackListFragment()

            var args = Bundle()

            args.putString("release_id", release_id)
            trackListFragment.arguments = args
            return trackListFragment
        }
    }
}