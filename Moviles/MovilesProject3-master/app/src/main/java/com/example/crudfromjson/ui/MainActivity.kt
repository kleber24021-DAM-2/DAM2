package com.example.crudfromjson.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudfromjson.R
import com.example.crudfromjson.data.MarvelRepository
import com.example.crudfromjson.databinding.ActivityMainBinding
import com.example.crudfromjson.domain.ownmodels.SuperHero
import com.example.crudfromjson.ui.herodetails.DetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var startDate : LocalDateTime
    private lateinit var endDate : LocalDateTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val heroesList = MarvelRepository(assets.open("marvel.json")).getList()
        val recyclerView = binding.mainRV
        val searchView = binding.searchView

        val customAdapter = HeroAdapter(heroesList, object : HeroAdapter.ButtonAction {
            override fun onClickEyeButton(id: Int) {
                this@MainActivity.onClickEyeButton(id)
            }

            override fun onClickEraseButton(superHero: SuperHero) {
                this@MainActivity.createConfirmationDialogToDelete(superHero)
            }
        })
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(this)

        setListeners(binding, customAdapter)
    }

    fun onClickEyeButton(superHeroId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(getString(R.string.intent_idName), superHeroId)
        startActivity(intent)
    }

    private fun eraseElement(hero: SuperHero) {
        val adapter = binding.mainRV.adapter as HeroAdapter
        val index = adapter.heroFilterList.indexOf(hero)
        adapter.heroFilterList.removeAt(index)
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

    private fun setListeners(currentBinding: ActivityMainBinding, adapter: HeroAdapter){
        currentBinding.startDateFilter.setOnClickListener(){
            showDatePickerDialog(currentBinding.startDateFilter)
        }
        currentBinding.endDateFilter.setOnClickListener(){
            showDatePickerDialog(currentBinding.endDateFilter);
        }
        currentBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }

        })
        currentBinding.filterByDate.setOnClickListener(){
            val filterList = adapter.heroFilterList
            val formatter = DateTimeFormatter.ofPattern("dd / MM / yyyy hh:mm").withResolverStyle(java.time.format.ResolverStyle.SMART)
            val endDate = LocalDateTime.parse(binding.endDateFilter.text.toString() + "00:00", formatter)
            filterList.filter {
                (it.dateModified.isAfter(startDate).and(it.dateModified.isBefore(endDate)))
            }
            adapter.heroFilterList.clear()
            adapter.heroFilterList.addAll(filterList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showDatePickerDialog(currentEditText : EditText) {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener{_, year, month, day ->
            val selectedDate = day.toString() + " / " + (month +1) + " / " + year
            currentEditText.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }
}