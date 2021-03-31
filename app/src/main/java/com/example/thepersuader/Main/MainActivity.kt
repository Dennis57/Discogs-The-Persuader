package com.example.thepersuader.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thepersuader.R
import com.example.thepersuader.ReleaseAdapter
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

        val adapter = ReleaseAdapter{
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.rvReleases.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.artist.observe(this, Observer {
            if(null != it) {
                binding.tvArtistName.text = it.name
                binding.tvRealName.text = it.real_name
                binding.tvAliases.text = it.aliases
            }
        })

        viewModel.releases.observe(this, Observer {
            if(null != it) {
                adapter.submitList(it)
            }
        })
    }
}