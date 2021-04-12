package com.example.thepersuader.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thepersuader.model.releaseDetail.VideosUiModel
import com.example.thepersuader.databinding.ItemVideoBinding


class VideoAdapter: ListAdapter<VideosUiModel, VideoAdapter.VideoViewHolder>(
    DiffCallback
){

    class VideoViewHolder(private var binding: ItemVideoBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(video: VideosUiModel) {
            binding.tvVideoTitle.text = video.title
            binding.ibVideo.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(video.uri));
                binding.root.context.startActivity(browserIntent)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<VideosUiModel>() {
        override fun areItemsTheSame(oldItem: VideosUiModel, newItem: VideosUiModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: VideosUiModel, newItem: VideosUiModel): Boolean {
            return oldItem.uri == newItem.uri
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video: VideosUiModel = getItem(position)
        holder.bind(video)
    }
}