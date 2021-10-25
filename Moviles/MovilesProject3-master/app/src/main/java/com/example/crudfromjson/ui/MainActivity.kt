package com.example.crudfromjson.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudfromjson.data.MarvelRepository
import com.example.crudfromjson.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

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
                override fun onClickButton(nombre: String) {
                    this@MainActivity.onClick(nombre)
                }
            })
            recyclerView.layoutManager =
                GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false)
        }

        val toast = Toast.makeText(this, heroesList[0].toString(), Toast.LENGTH_LONG)
        toast.show()
    }

    fun onClick(nombre: String) {
        Snackbar.make(
            binding.mainRV, " $nombre", Snackbar.LENGTH_SHORT
        ).show()
    }

}