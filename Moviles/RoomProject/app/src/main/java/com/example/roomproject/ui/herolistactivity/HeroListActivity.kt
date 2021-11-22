package com.example.roomproject.ui.herolistactivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.example.roomproject.R
import com.example.roomproject.databinding.ActivityHeroListBinding
import com.example.roomproject.ui.addheroactivity.AddHeroActivity
import com.example.roomproject.ui.herodetailactivity.HeroDetailActivity
import com.example.roomproject.ui.updateheroactivity.UpdateHeroActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.util.Pair as UtilPair

@AndroidEntryPoint
class HeroListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeroListBinding
    private lateinit var heroAdapter: HeroAdapter

    private val viewModel: HeroesViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_add_button -> {
                val intent = Intent(this, AddHeroActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        heroAdapter = HeroAdapter(object : HeroAdapter.ButtonActions {
            override fun onClickEyeButton(id: Int, imageView: ImageView, title: TextView) {
                seeHeroDetails(id, imageView, title)
            }

            override fun onClickEditButton(id: Int, imageView: ImageView) {
                updateHero(id, imageView)
            }

            override fun onClickEraseButton(id: Int) {
                deleteHero(id)
            }

        })


        binding.rvHeroes.adapter = heroAdapter

        setViewModelObservers()


        viewModel.getHeroes()
    }

    private fun setViewModelObservers() {
        viewModel.heroes.observe(this, {
            heroAdapter.submitList(it)
        })
        viewModel.error.observe(this, {
            if (!it) {
                Toast.makeText(this, getString(R.string.errorDatabase), Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onResume() {
        super.onResume()
        viewModel.getHeroes()
    }

    private fun seeHeroDetails(heroId: Int, view: ImageView, title: TextView) {
        val intent = Intent(this, HeroDetailActivity::class.java)
        intent.putExtra(getString(R.string.heroIdExtra), heroId)
        if (Build.VERSION.SDK_INT > 26) {
            intent.putExtra(getString(R.string.imageTransition), view.transitionName)
            intent.putExtra(getString(R.string.textTransition), title.transitionName)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                UtilPair.create(view, ViewCompat.getTransitionName(view).toString()),
                UtilPair.create(title, ViewCompat.getTransitionName(title).toString())
            )
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    private fun deleteHero(heroId: Int) {
        viewModel.deleteHeroes(heroId)
    }

    private fun updateHero(heroId: Int, view: ImageView) {
        //Esta es una animación, pero con Android 8 (API 26 y 27) peta, si puedes miralo con la tablet :)
        val intent = Intent(this, UpdateHeroActivity::class.java)
        intent.putExtra(getString(R.string.heroIdExtra), heroId)
        if (Build.VERSION.SDK_INT > 26) {
            val transitionName: String = view.transitionName
            intent.putExtra(getString(R.string.imageTransition), transitionName)
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view,
                transitionName
            )
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }
}