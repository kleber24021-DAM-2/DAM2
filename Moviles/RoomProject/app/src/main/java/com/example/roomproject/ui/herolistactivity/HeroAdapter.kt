package com.example.roomproject.ui.herolistactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.roomproject.R
import com.example.roomproject.databinding.ViewHeroBinding
import com.example.roomproject.domain.SuperHeroDisplay
import com.example.roomproject.ui.ConstantsUI

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
        fun onClickEyeButton(id: Int, imageView: ImageView, title:TextView)
        fun onClickEditButton(id: Int, imageView: ImageView)
        fun onClickEraseButton(id: Int)
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewHeroBinding.bind(itemView)

        fun bind(item: SuperHeroDisplay, buttonActions: ButtonActions) = with(binding) {
            imageview.load(item.imageUrl)
            tvName.text = item.name
            //Ponemos los nombres de las transiciones a cada elemento
            imageview.transitionName = item.name.plus(item.id).plus(ConstantsUI.IMAGEVIEW)
            tvName.transitionName = item.name.plus(item.id).plus(ConstantsUI.TITLEVIEW)
            viewButton.setOnClickListener {
                buttonActions.onClickEyeButton(item.id, binding.imageview, binding.tvName)
            }
            editButton.setOnClickListener {
                buttonActions.onClickEditButton(item.id, binding.imageview)
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
