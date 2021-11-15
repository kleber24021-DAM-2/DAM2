package com.example.crudfromjson.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudfromjson.R
import com.example.crudfromjson.data.MarvelRepository
import com.example.crudfromjson.databinding.ActivityMainBinding
import com.example.crudfromjson.domain.ownmodels.SuperHero
import com.example.crudfromjson.ui.herodetails.DetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var heroesList: List<SuperHero>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Creamos el binding y pintamos el elemento root
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Cargamos la lista del repository y pintamos el Recyclerview
        heroesList = MarvelRepository(assets.open(getString(R.string.jsonName))).getList()
        updateRecyclerView(heroesList)

        //Cargamos los listeners
        setListeners()
    }

    private fun updateRecyclerView(list: List<SuperHero>) {
        //Cargamos el RecyclerView con la lista pasada
        list.let {
            binding.mainRV.adapter = HeroAdapter(list, object : HeroAdapter.ButtonAction {
                override fun onClickEyeButton(id: Int) {
                    this@MainActivity.onClickEyeButton(id)
                }

                override fun onClickEraseButton(superHero: SuperHero) {
                    this@MainActivity.createConfirmationDialogToDelete(superHero)
                }
            })
            binding.mainRV.layoutManager = LinearLayoutManager(this)
        }
        //Se vuelve a poner los QueryTextListener, para actualizar los adapters
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val adapter = binding.mainRV.adapter as HeroAdapter
                adapter.filter.filter(p0)
                return false
            }

        })
    }

    private fun setListeners() {
        val adapter = binding.mainRV.adapter as HeroAdapter
        binding.startDateFilter.setOnClickListener {
            showDatePickerDialog(binding.startDateFilter)
        }
        binding.endDateFilter.setOnClickListener {
            showDatePickerDialog(binding.endDateFilter)
        }
        binding.filterByDate.setOnClickListener {
            if (binding.startDateFilter.text.isNotEmpty() && binding.endDateFilter.text.isNotEmpty()) {
                val formatter = DateTimeFormatter.ofPattern("dd / MM / yyyyHH:mm")
                val startDate =
                    LocalDateTime.parse(
                        (binding.startDateFilter.text.toString() + getString(R.string.dateAppend)),
                        formatter
                    )
                val endDate =
                    LocalDateTime.parse(
                        (binding.endDateFilter.text.toString() + getString(R.string.dateAppend)),
                        formatter
                    )
                val filterList = mutableListOf<SuperHero>()
                for (hero in adapter.heroFilterList) {
                    if (hero.dateModified.isBefore(endDate) && hero.dateModified.isAfter(startDate)) {
                        filterList.add(hero)
                    }
                }
                updateRecyclerView(filterList)
            } else {
                Toast.makeText(this, getString(R.string.error_emptyDate), Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonClearFilter.setOnClickListener {
            binding.startDateFilter.text.clear()
            binding.endDateFilter.text.clear()
            updateRecyclerView(heroesList)

        }
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
            binding.startDateFilter.visibility = View.VISIBLE
            binding.endDateFilter.visibility = View.VISIBLE
            binding.buttonClearFilter.visibility = View.VISIBLE
            binding.filterByDate.visibility = View.VISIBLE
        }
        binding.searchView.setOnCloseListener {
            binding.startDateFilter.visibility = View.GONE
            binding.endDateFilter.visibility = View.GONE
            binding.buttonClearFilter.visibility = View.GONE
            binding.filterByDate.visibility = View.GONE
            false
        }
    }

    private fun onClickEyeButton(superHeroId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(getString(R.string.intent_idName), superHeroId)
        startActivity(intent)
    }

    private fun createConfirmationDialogToDelete(toEraseSuperHero: SuperHero) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.atention))
            .setMessage(getString(R.string.confirmation_erase))
            .setPositiveButton(getString(R.string.si)) { view, _ ->
                eraseElement(toEraseSuperHero)
                view.dismiss()
            }
            .setNegativeButton(getString(R.string.no)) { view, _ ->
                view.dismiss()
            }
        dialog.show()
    }

    private fun eraseElement(hero: SuperHero) {
        val adapter = binding.mainRV.adapter as HeroAdapter
        val index = adapter.heroFilterList.indexOf(hero)
        val indexAtRepository = MarvelRepository().getList().indexOf(hero)
        adapter.heroFilterList.removeAt(index)
        MarvelRepository().deleteHero(hero)
        binding.mainRV.adapter?.notifyItemRemoved(index)

        Snackbar.make(binding.searchView, getString(R.string.message_undo), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.UNDO)) {
                MarvelRepository().addHero(indexAtRepository, hero)
                adapter.heroFilterList.add(index, hero)
                binding.mainRV.adapter?.notifyItemInserted(index)
            }.show()
    }

    private fun showDatePickerDialog(currentText: TextView) {
        val newFragment = DatePickerFragment.newInstance { _, year, month, day ->
            val selectedDate: String = if (day < 10 && month + 1 < 10) {
                getString(R.string.datePlaceHolderDayMonthCorrection, day, month + 1, year)
            } else if (day < 10) {
                getString(R.string.datePlaceHolderDayCorrection, day, month + 1, year)
            } else if (month + 1 < 10) {
                getString(R.string.datePlaceHolderMonthCorrection, day, month + 1, year)
            } else {
                getString(R.string.datePlaceHolder, day, month + 1, year)
            }
            currentText.text = selectedDate
        }
        newFragment.show(supportFragmentManager, "datePicker")
    }
}