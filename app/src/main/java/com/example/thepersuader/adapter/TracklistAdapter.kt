package com.example.thepersuader.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thepersuader.model.releaseDetail.TrackListUiModel
import com.example.thepersuader.databinding.ItemTracklistBinding

class TracklistAdapter: ListAdapter<TrackListUiModel, TracklistAdapter.TracklistViewHolder>(
    DiffCallback
){

    class TracklistViewHolder(private var binding: ItemTracklistBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: TrackListUiModel) {
            binding.tvTracklistTitle.text = track.title
            binding.tvTracklistDuration.text = track.duration
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TrackListUiModel>() {
        override fun areItemsTheSame(oldItem: TrackListUiModel, newItem: TrackListUiModel): Boolean {
            return oldItem.title == oldItem.title
        }

        override fun areContentsTheSame(oldItem: TrackListUiModel, newItem: TrackListUiModel): Boolean {
            return oldItem.title == newItem.title && oldItem.duration == newItem.duration
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracklistViewHolder {
        return TracklistViewHolder(
            ItemTracklistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TracklistViewHolder, position: Int) {
        val track: TrackListUiModel = getItem(position)
        holder.bind(track)
    }
}