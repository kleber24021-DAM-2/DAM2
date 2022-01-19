package com.example.seriesfollower.ui.series

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.seriesfollower.GeneralConstants
import com.example.seriesfollower.R
import com.example.seriesfollower.databinding.FragmentSeriesDetailBinding
import com.example.seriesfollower.ui.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesDetailFragment : Fragment() {

    private var _binding: FragmentSeriesDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SeriesViewModel by viewModels()
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var seriesUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeriesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: SeriesDetailFragmentArgs by navArgs()
        imageAdapter = ImageAdapter(binding.root.context)
        setObservers()
        setListeners()
        viewModel.getSeriesById(args.seriesId)
    }

    private fun setObservers() {
        viewModel.series.observe(this, {
            with(binding) {
                tvMovieTitle.text = it.title
                tvMovieResume.text = it.overview
                ratingBarSeries.rating = it.voteAverage.toFloat()
                seriesUrl = it.homePage

                Glide.with(this@SeriesDetailFragment)
                    .load(it.mainImage)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.outline_error_outline_24)
                    .into(seriesImageView)
            }
        })
        viewModel.isFavorite.observe(this, {
            with(binding) {
                if (it) {
                    buttonFavSeries.setImageResource(R.drawable.ic_baseline_favorite_24)
                    buttonFavSeries.setColorFilter(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.red
                        )
                    )
                } else {
                    buttonFavSeries.setImageResource(R.drawable.outline_favorite_border_24)
                    buttonFavSeries.colorFilter = null
                }
            }
        })
        viewModel.error.observe(this, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setListeners() {
        binding.btnInfo.setOnClickListener {
            openNewTabWindow(seriesUrl, binding.root.context)
        }
        binding.buttonFavSeries.setOnClickListener {
            viewModel.isFavorite.value?.let {
                if (it) {
                    viewModel.removeFavoriteSeries()
                } else {
                    viewModel.addFavoriteSeries()
                }
            }
        }
    }

    private fun openNewTabWindow(url: String, context: Context) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        val b = Bundle()
        b.putBoolean(GeneralConstants.NEW_WINDOW, true)
        intent.putExtras(b)
        context.startActivity(intent)
    }

}