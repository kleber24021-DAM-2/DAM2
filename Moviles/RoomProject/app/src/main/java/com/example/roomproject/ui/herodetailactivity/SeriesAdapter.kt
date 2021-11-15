package com.example.roomproject.ui.herodetailactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomproject.R
import com.example.roomproject.data.Constants
import com.example.roomproject.databinding.ViewSeriesBinding
import com.example.roomproject.domain.Series
import java.time.format.DateTimeFormatter


class SeriesAdapter : ListAdapter<Series, SeriesAdapter.ItemViewholder>(DiffCallbackSeries()) {

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewSeriesBinding.bind(itemView)
        fun bind(item: Series) = with(binding) {
            seriesId.text = item.id.toString()
            seriesName.text = item.name
            seriesDate.text =
                item.publishedDate.format(DateTimeFormatter.ofPattern(Constants.formatter))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_series, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class DiffCallbackSeries : DiffUtil.ItemCallback<Series>() {
    override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
        return oldItem == newItem
    }

}