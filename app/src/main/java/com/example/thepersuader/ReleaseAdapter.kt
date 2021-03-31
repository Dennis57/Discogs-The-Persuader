package com.example.thepersuader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thepersuader.Model.Release.ReleaseUiModel
import com.example.thepersuader.databinding.ItemReleasesBinding

class ReleaseAdapter(val onClickListener: (id: Int) -> Unit) : ListAdapter<ReleaseUiModel, ReleaseAdapter.ReleaseViewHolder>(DiffCallback){

    inner class ReleaseViewHolder(private var binding: ItemReleasesBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(release: ReleaseUiModel) {
            binding.tvReleaseName.text = release.name
            binding.tvReleaseYear.text = release.year.toString()
            binding.root.setOnClickListener {
                onClickListener(release.id)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ReleaseUiModel>() {
        override fun areItemsTheSame(oldItem: ReleaseUiModel, newItem: ReleaseUiModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ReleaseUiModel, newItem: ReleaseUiModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleaseViewHolder {
        return ReleaseViewHolder(ItemReleasesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ReleaseViewHolder, position: Int) {
        val release: ReleaseUiModel = getItem(position)
        holder.bind(release)
    }
}