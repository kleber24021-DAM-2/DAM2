package com.example.seriesfollower.ui.favorites

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.seriesfollower.R
import com.example.seriesfollower.databinding.ViewFavmoviesBinding
import com.example.seriesfollower.domain.model.favorite.FavoriteItem

class FavAdapter(
    val context: Context,
    val actions: FavActions
) : ListAdapter<FavoriteItem, FavAdapter.FavMoviesHolder>(DiffCallback()) {

    private var selectedMode: Boolean = false

    interface FavActions {
        fun showFavDetails(item: FavoriteItem)
        fun isItemSelected(item: FavoriteItem): Boolean
        fun selectItem(item: FavoriteItem)
        fun deselectItem(item: FavoriteItem)
        fun noSelectedItems(): Boolean
        fun startSelectMode()
        fun quitSelectMode()
        fun deleteFav(item: FavoriteItem)
        fun changeBarNumber()
    }

    inner class FavMoviesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewFavmoviesBinding.bind(itemView)

        fun bind(item: FavoriteItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.poster)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.outline_error_outline_24)
                    .into(favSeriesImageView)
                tvTitleFavMovie.text = item.title
                if (!selectedMode) {
                    itemView.setBackgroundColor(Color.WHITE)
                }
            }

            itemView.setOnLongClickListener {
                if (!selectedMode) {
                    selectedMode = true
                    actions.selectItem(item)
                    itemView.setBackgroundColor(Color.RED)
                    actions.startSelectMode()
                    actions.changeBarNumber()
                    notifyDataSetChanged()
                }
                true
            }

            itemView.setOnClickListener {
                if (selectedMode) {
                    if (actions.isItemSelected(item)) {
                        actions.deselectItem(item)
                        itemView.setBackgroundColor(Color.WHITE)
                        if (actions.noSelectedItems()) {
                            resetSelectMode()
                        }
                    } else {
                        actions.selectItem(item)
                        itemView.setBackgroundColor(Color.RED)
                    }
                    actions.changeBarNumber()
                } else {
                    actions.showFavDetails(item)
                }
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<FavoriteItem>() {
        override fun areItemsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMoviesHolder {
        return FavMoviesHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_favmovies, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavMoviesHolder, position: Int) {
        with(holder) {
            val item = getItem(position)
            bind(item)
        }
    }

    fun resetSelectMode() {
        actions.quitSelectMode()
        selectedMode = false
        notifyDataSetChanged()
    }

    val swipeGesture = object : SwipeGesture(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (!selectedMode) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        actions.deleteFav(currentList[viewHolder.adapterPosition])
                    }
                }
            }
        }

    }
}