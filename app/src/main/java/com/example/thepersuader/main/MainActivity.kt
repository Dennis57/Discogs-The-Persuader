package com.example.thepersuader.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thepersuader.ViewModelFactory
import com.example.thepersuader.adapter.ReleaseAdapter
import com.example.thepersuader.releaseDetail.ReleaseDetailActivity
import com.example.thepersuader.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        // Initialize Data Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bind to Adapter
        binding.rvReleases.layoutManager = LinearLayoutManager(this)

        val adapter = ReleaseAdapter {
            Log.e("<Error>", "Tapped release: $it")
            val intent = Intent(this@MainActivity, ReleaseDetailActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }

        binding.rvReleases.adapter = adapter

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.artist.observe(this, Observer {
            if (null != it) {
                binding.tvArtistName.text = it.name
                binding.tvRealName.text = it.real_name
                binding.tvAliases.text = it.aliases
            }
        })

        viewModel.showLoading.observe(this, Observer {
            if (it == true) {
                binding.tvArtistName.visibility = View.INVISIBLE
                binding.tvRealName.visibility = View.INVISIBLE
                binding.tvAliases.visibility = View.INVISIBLE
                binding.prArtistName.visibility = View.VISIBLE
            } else {
                binding.tvArtistName.visibility = View.VISIBLE
                binding.tvRealName.visibility = View.VISIBLE
                binding.tvAliases.visibility = View.VISIBLE
                binding.prArtistName.visibility = View.GONE
            }
        })

        viewModel.artistError.observe(this, Observer {
            if (it == true) {
                binding.ivError.visibility = View.VISIBLE
                binding.tvArtistName.visibility = View.GONE
                binding.tvRealName.visibility = View.GONE
                binding.tvAliases.visibility = View.GONE
            } else {
                binding.ivError.visibility = View.GONE
            }
        })

        viewModel.releases.observe(this, Observer {
            if (null != it) {
                adapter.submitList(it.toMutableList())
            }
        })

        binding.rvReleases.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    viewModel.getReleases()
                }
            }
        })

        viewModel.getArtist()
        viewModel.getReleases()

    }
}