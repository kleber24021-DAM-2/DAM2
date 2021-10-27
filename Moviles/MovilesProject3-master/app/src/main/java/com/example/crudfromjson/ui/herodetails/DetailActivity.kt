package com.example.crudfromjson.ui.herodetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.crudfromjson.R
import com.example.crudfromjson.data.MarvelRepository
import com.example.crudfromjson.databinding.ActivityDetailBinding
import com.example.crudfromjson.ui.DetailsAdapter

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val heroeId = intent.getIntExtra(getString(R.string.intent_idName), -1)
        val heroe = MarvelRepository().getList().first {
            it.id == heroeId
        }

        binding.imageView.load(heroe.imageUrl)

        val comicRecyclerView = binding.comicRecyclerView
        comicRecyclerView.adapter = DetailsAdapter(heroe.comicsName)
        comicRecyclerView.layoutManager = LinearLayoutManager(this)
        comicRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val seriesRecyclerView = binding.seriesRecyclerView
        seriesRecyclerView.adapter = DetailsAdapter(heroe.seriesName)
        seriesRecyclerView.layoutManager = LinearLayoutManager(this)
        seriesRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}

