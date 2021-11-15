package com.example.roomproject.ui.herolistactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.roomproject.R
import com.example.roomproject.databinding.ViewHeroBinding
import com.example.roomproject.domain.SuperHeroDisplay

class HeroAdapter(private val buttonActions: ButtonActions) :
    ListAdapter<SuperHeroDisplay, HeroAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_hero, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item, buttonActions)
    }

    interface ButtonActions {
        fun onClickEyeButton(id: Int)
        fun onClickEditButton(id: Int)
        fun onClickEraseButton(id: Int)
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewHeroBinding.bind(itemView)

        fun bind(item: SuperHeroDisplay, buttonActions: ButtonActions) = with(binding) {
            imageview.load(item.imageUrl)
            tvName.text = item.name
            viewButton.setOnClickListener {
                buttonActions.onClickEyeButton(item.id)
            }
            editButton.setOnClickListener {
                buttonActions.onClickEditButton(item.id)
            }
            deleteButton.setOnClickListener {
                buttonActions.onClickEraseButton(item.id)
            }

        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<SuperHeroDisplay>() {
    override fun areItemsTheSame(oldItem: SuperHeroDisplay, newItem: SuperHeroDisplay): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SuperHeroDisplay, newItem: SuperHeroDisplay): Boolean {
        return oldItem == newItem
    }
}
