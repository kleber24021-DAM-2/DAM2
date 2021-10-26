package com.example.crudfromjson.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudfromjson.R
import com.example.crudfromjson.data.MarvelRepository
import com.example.crudfromjson.databinding.ActivityMainBinding
import com.example.crudfromjson.domain.ownmodels.SuperHero
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val heroesList = MarvelRepository(assets.open("marvel.json")).getList()
        val recyclerView = binding.mainRV
        heroesList.let {
            recyclerView.adapter = HeroAdapter(it, object : HeroAdapter.ButtonAction {
                override fun onClickEyeButton(id: Int) {
                    this@MainActivity.onClickEyeButton(id)
                }

                override fun onClickEraseButton(superHero: SuperHero) {
                    this@MainActivity.onClickEraseButton(superHero)
                }
            })
            recyclerView.layoutManager =
                GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false)
        }
    }

    fun onClickEyeButton(superHeroId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(getString(R.string.intent_idName), superHeroId)
        startActivity(intent)
    }

    fun onClickEraseButton(toEraseSuperHero: SuperHero) {
        createConfirmationDialogToDelete(toEraseSuperHero)
    }

    private fun eraseElement(hero: SuperHero) {
        val index = MarvelRepository().getList().indexOf(hero)
        MarvelRepository().deleteHero(hero)
        binding.mainRV.adapter?.notifyItemRemoved(index)
    }

    private fun createConfirmationDialogToDelete(toEraseSuperHero: SuperHero) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.atention))
            .setMessage(getString(R.string.confirmation_erase))
            .setPositiveButton("Si") { view, _ ->
                eraseElement(toEraseSuperHero)
                view.dismiss()
            }
            .setNegativeButton("No") { view, _ ->
                view.dismiss()
            }


        dialog.show()
    }


}