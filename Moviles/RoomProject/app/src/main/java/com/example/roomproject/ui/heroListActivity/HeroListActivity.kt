package com.example.roomproject.ui.heroListActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.roomproject.databinding.ActivityHeroListBinding

class HeroListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeroListBinding
    private lateinit var heroAdapter: HeroAdapter

    private val viewModel: MainViewModel by viewModels{

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        heroAdapter = HeroAdapter()
        binding.rvHeroes.adapter = heroAdapter

        viewModel.observes
    }
}