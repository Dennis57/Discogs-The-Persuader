package com.example.thepersuader.Main

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thepersuader.Adapter.ReleaseAdapter
import com.example.thepersuader.ReleaseDetail.ReleaseDetailActivity
import com.example.thepersuader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Data Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bind to Adapter
        binding.rvReleases.layoutManager = LinearLayoutManager(this)

        val adapter = ReleaseAdapter {
            val intent = Intent(this@MainActivity, ReleaseDetailActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }

        binding.rvReleases.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.artist.observe(this, Observer {
            if (null != it) {
                binding.tvArtistName.text = it.name
                binding.tvRealName.text = it.real_name
                binding.tvAliases.text = it.aliases
            }
        })

        viewModel.showLoading.observe(this, Observer {
            Log.e("<Error>", it.toString())
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
                binding.tvArtistName.visibility = View.INVISIBLE
                binding.tvRealName.visibility = View.INVISIBLE
                binding.tvAliases.visibility = View.INVISIBLE
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

    }
}