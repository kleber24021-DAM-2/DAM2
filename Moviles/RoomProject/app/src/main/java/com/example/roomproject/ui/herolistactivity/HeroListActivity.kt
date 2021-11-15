package com.example.roomproject.ui.herolistactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.roomproject.R
import com.example.roomproject.databinding.ActivityHeroListBinding
import com.example.roomproject.ui.addheroactivity.AddHeroActivity
import com.example.roomproject.ui.herodetailactivity.HeroDetailActivity
import com.example.roomproject.ui.updateheroactivity.UpdateHeroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeroListBinding
    private lateinit var heroAdapter: HeroAdapter

    private val viewModel: HeroesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        heroAdapter = HeroAdapter(object : HeroAdapter.ButtonActions {
            override fun onClickEyeButton(id: Int) {
                seeHeroDetails(id)
            }

            override fun onClickEditButton(id: Int) {
                updateHero(id)
            }

            override fun onClickEraseButton(id: Int) {
                deleteHero(id)
            }

        })
        viewModel.heroes.observe(this, {
            heroAdapter.submitList(it)
        })

        binding.rvHeroes.adapter = heroAdapter



        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddHeroActivity::class.java)
            startActivity(intent)
        }

        viewModel.getHeroes()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHeroes()
    }

    fun seeHeroDetails(heroId: Int) {
        val intent = Intent(this, HeroDetailActivity::class.java)
        intent.putExtra(getString(R.string.heroIdExtra), heroId)
        startActivity(intent)
    }

    fun deleteHero(heroId: Int) {
        viewModel.deleteHeroes(heroId)
    }

    fun updateHero(heroId: Int) {
        val intent = Intent(this, UpdateHeroActivity::class.java)
        intent.putExtra(getString(R.string.heroIdExtra), heroId)
        startActivity(intent)
    }
}