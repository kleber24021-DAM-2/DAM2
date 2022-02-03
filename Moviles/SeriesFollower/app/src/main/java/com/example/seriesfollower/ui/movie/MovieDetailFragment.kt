package com.example.seriesfollower.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.seriesfollower.R
import com.example.seriesfollower.databinding.FragmentMovieDetailBinding
import com.example.seriesfollower.domain.model.movies.OwnMovie
import com.example.seriesfollower.ui.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: MovieDetailFragmentArgs by navArgs()
        imageAdapter = ImageAdapter(binding.root.context)
        setObservers()
        viewModel.handleEvent(MoviesContract.Event.GetMovie(args.movieId))
        bindImageRV()
        setListeners()
    }

    private fun bindImageRV() {
        with(binding.rvImages) {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = imageAdapter
        }
    }

    private fun setObservers() {

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.movie?.let {
                        bindMovie(it)
                    }
                    bindFavoriteButton(state.isFavorite)

                    binding.loading.visibility =
                        if (state.isLoading){
                            View.VISIBLE
                        }else{
                            View.GONE
                        }

                    state.error?.let {
                        Toast.makeText(binding.root.context, it, Toast.LENGTH_SHORT).show()
                        viewModel.handleEvent(MoviesContract.Event.ErrorMostrado)
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.buttonFavMovies.setOnClickListener {
            if (viewModel.uiState.value.isFavorite) {
                viewModel.handleEvent(MoviesContract.Event.RemoveMovieFavorite)
            } else {
                viewModel.handleEvent(MoviesContract.Event.AddMovieToFavorite)
            }
        }
    }

    private fun bindMovie(actualMovie: OwnMovie) {
        with(binding) {
            tvMovieTitle.text = actualMovie.title
            tvMovieResume.text = actualMovie.overview
            Glide.with(this@MovieDetailFragment)
                .load(actualMovie.poster)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.outline_error_outline_24)
                .into(movieImage)
            ratingBar.rating = actualMovie.voteAverage.toFloat()
        }
    }

    private fun bindFavoriteButton(isFavorite : Boolean){
        with(binding){
            if (isFavorite) {
                buttonFavMovies.setImageResource(R.drawable.ic_baseline_favorite_24)
                buttonFavMovies.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.red
                    )
                )
            } else {
                buttonFavMovies.setImageResource(R.drawable.outline_favorite_border_24)
                buttonFavMovies.colorFilter = null
            }
        }
    }
}