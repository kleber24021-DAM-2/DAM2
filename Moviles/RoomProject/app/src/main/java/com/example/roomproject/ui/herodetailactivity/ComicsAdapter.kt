package com.example.roomproject.ui.herodetailactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomproject.R
import com.example.roomproject.data.Constants
import com.example.roomproject.databinding.ViewComicBinding
import com.example.roomproject.domain.Comic
import java.time.format.DateTimeFormatter

class ComicsAdapter :
    ListAdapter<Comic, ComicsAdapter.ItemViewholder>(DiffCallbackComics()) {

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewComicBinding.bind(itemView)
        fun bind(item: Comic) = with(binding) {
            comicId.text = item.id.toString()
            comicDate.text =
                item.publishedDate.format(DateTimeFormatter.ofPattern(Constants.formatter))
            comicName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_comic, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class DiffCallbackComics : DiffUtil.ItemCallback<Comic>() {
    override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
        return oldItem == newItem
    }

}