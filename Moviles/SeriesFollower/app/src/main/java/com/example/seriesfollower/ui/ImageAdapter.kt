package com.example.seriesfollower.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.seriesfollower.R
import com.example.seriesfollower.databinding.ViewImageBinding

class ImageAdapter(val context: Context) :
    ListAdapter<String, ImageAdapter.ImageViewHolder>(DiffCallback()) {
    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewImageBinding.bind(itemView)
        fun bind(item: String) {
            Glide.with(itemView)
                .load(item)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.outline_error_outline_24)
                .into(binding.imageView2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_image, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        with(holder) {
            val item = getItem(position)
            bind(item)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}