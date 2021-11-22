package com.example.roomproject.ui.herodetailactivity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.roomproject.R
import com.example.roomproject.data.Constants
import com.example.roomproject.databinding.ActivityHeroDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class HeroDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroDetailBinding
    private lateinit var comicsAdapter: ComicsAdapter
    private lateinit var seriesAdapter: SeriesAdapter


    private val superHeroViewModel: HeroDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val heroId = intent.getIntExtra(getString(R.string.heroIdExtra), -1)

        comicsAdapter = ComicsAdapter()
        seriesAdapter = SeriesAdapter()

        with(binding) {
            rvComics.adapter = comicsAdapter
            rvSeries.adapter = seriesAdapter
        }
        setViewModelObservers()

        superHeroViewModel.getCompleteHero(heroId)

        if(Build.VERSION.SDK_INT > 26){
            val imageTransitionName = intent.getStringExtra(getString(R.string.imageTransition))
            val nameTextTransitionName = intent.getStringExtra(getString(R.string.textTransition))
            binding.imageView.transitionName = imageTransitionName
            binding.tvName.transitionName = nameTextTransitionName
        }
    }

    private fun setViewModelObservers(){
        superHeroViewModel.superHero.observe(this, {
            with(binding) {
                imageView.load(it.imageUrl)

                tvName.text = it.name
                tvDate.text =
                    it.modifiedDate.format(DateTimeFormatter.ofPattern(Constants.formatter))
                descriptionTextView.text = it.description
                comicsAdapter.submitList(it.comicsList)
                seriesAdapter.submitList(it.seriesList)
            }
        })
        superHeroViewModel.error.observe(this, {
            if (!it){
                Toast.makeText(this, getString(R.string.errorDatabase), Toast.LENGTH_SHORT).show()
            }
        })
    }
}