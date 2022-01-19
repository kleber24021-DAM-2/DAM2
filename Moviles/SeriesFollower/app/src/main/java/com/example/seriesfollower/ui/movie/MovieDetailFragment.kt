package com.example.seriesfollower.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.seriesfollower.R
import com.example.seriesfollower.databinding.FragmentMovieDetailBinding
import com.example.seriesfollower.ui.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding : FragmentMovieDetailBinding? = null
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
        viewModel.getMovieById(args.movieId)
        bindImageRV()
        setListeners()
    }

    private fun bindImageRV(){
        with(binding.rvImages){
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = imageAdapter
        }
    }

    private fun setObservers(){
        viewModel.movie.observe(this, {
            with(binding){
                tvMovieTitle.text = it.title
                tvMovieResume.text = it.overview
                Glide.with(this@MovieDetailFragment)
                    .load(it.poster)
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.outline_error_outline_24)
                    .into(movieImage)
                ratingBar.rating = it.voteAverage.toFloat()
            }
        })
        viewModel.isFavorite.observe(this, {
            if (it){
                with(binding){
                    buttonFavMovies.setImageResource(R.drawable.ic_baseline_favorite_24)
                    buttonFavMovies.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.red))
                }
            }else{
                with(binding){
                    buttonFavMovies.setImageResource(R.drawable.outline_favorite_border_24)
                    buttonFavMovies.colorFilter = null
                }
            }
        })
        viewModel.error.observe(this, {
            Toast.makeText(activity,it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setListeners(){
        binding.buttonFavMovies.setOnClickListener{
            viewModel.isFavorite.value?.let {
                if (it){
                    viewModel.removeFavoriteMovie()
                }else{
                    viewModel.addFavoriteMovie()
                }
            }
        }
    }
}