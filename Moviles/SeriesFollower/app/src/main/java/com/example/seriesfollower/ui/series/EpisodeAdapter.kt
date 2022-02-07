package com.example.seriesfollower.ui.series

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seriesfollower.R
import com.example.seriesfollower.databinding.ViewOwnepisodeBinding
import com.example.seriesfollower.domain.model.series.episode.OwnEpisode

class EpisodeAdapter(
    val context: Context
) : ListAdapter<OwnEpisode, EpisodeAdapter.OwnEpisodeHolder>(DiffCallback()) {

    inner class OwnEpisodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ViewOwnepisodeBinding.bind(itemView)

        fun bind(item: OwnEpisode){
            Glide.with(itemView)
                .load(item.image)
                .fitCenter()
                .error(R.drawable.outline_error_outline_24)
                .into(binding.imageEpisode)
            binding.tvEpisodeTitle.text = item.name
            binding.tvEpisodeInfo.text = item.seasonNum.toString() + " " + item.airDate.toString()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<OwnEpisode>(){
        override fun areItemsTheSame(oldItem: OwnEpisode, newItem: OwnEpisode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OwnEpisode, newItem: OwnEpisode): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnEpisodeHolder {
        return OwnEpisodeHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_ownepisode, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OwnEpisodeHolder, position: Int) {
        with(holder){
            val item = getItem(position)
            bind(item)
        }
    }
}
