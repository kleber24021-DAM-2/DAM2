package com.example.seriesfollower.ui.utils.query

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
import com.example.seriesfollower.databinding.ViewResultBinding
import com.example.seriesfollower.domain.model.queryresult.OwnResult

class ResultAdapter(
    val context: Context,
    private val actions: OwnResultActions
) : ListAdapter<OwnResult, ResultAdapter.ResultViewHolder>(DiffCallback()) {
    interface OwnResultActions {
        fun showResultDetails(item: OwnResult)
    }

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewResultBinding.bind(itemView)

        fun bind(item: OwnResult, actions: OwnResultActions) {
            with(binding) {
                Glide.with(itemView)
                    .load(item.mainImage)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.outline_error_outline_24)
                    .into(imageViewMainImage)
                tvTitle.text = item.title
                imageViewMainImage.setOnClickListener {
                    actions.showResultDetails(item)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_result, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        with(holder) {
            val item = getItem(position)
            bind(item, actions)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<OwnResult>() {
        override fun areItemsTheSame(oldItem: OwnResult, newItem: OwnResult): Boolean {
            return oldItem.id == newItem.id && oldItem.resultType == newItem.resultType
        }

        override fun areContentsTheSame(oldItem: OwnResult, newItem: OwnResult): Boolean {
            return oldItem == newItem
        }

    }
}