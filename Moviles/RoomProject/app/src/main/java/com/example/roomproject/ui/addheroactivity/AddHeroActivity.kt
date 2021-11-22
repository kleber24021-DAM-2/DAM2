package com.example.roomproject.ui.addheroactivity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.roomproject.R
import com.example.roomproject.databinding.ActivityAddHeroBinding
import com.example.roomproject.domain.Comic
import com.example.roomproject.domain.Series
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.ui.datePicker.DatePickerFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AddHeroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddHeroBinding
    private val addHeroViewModel: AddHeroViewModel by viewModels()

    private lateinit var comicAdapter: StringAdapter
    private lateinit var seriesAdapter: StringAdapter


    private val comicsList: MutableList<Comic> = mutableListOf()
    private val seriesList: MutableList<Series> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHeroBinding.inflate(layoutInflater)

        comicAdapter = StringAdapter()
        seriesAdapter = StringAdapter()

        setContentView(binding.root)
        setListeners()
        setViewModelObservers()
        with(binding) {
            rvHeroComics.adapter = comicAdapter
            rvHeroSeries.adapter = seriesAdapter
        }
    }



    private fun setListeners() {
        with(binding) {
            comicsButton.setOnClickListener {
                createNewComic()
                clearComicText()
            }
            seriesButton.setOnClickListener {
                createNewSeries()
                clearComicText()
            }
            addButton.setOnClickListener {
                createNewHero()
            }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                binding.textImageUrl.isEnabled = !isChecked
            }
            heroDate.setOnClickListener {
                showDatePickerDialog(heroDate)
            }
            addComicDate.setOnClickListener {
                showDatePickerDialog(addComicDate)
            }
        }
    }

    private fun setViewModelObservers(){
        addHeroViewModel.response.observe(this, {
            if (it){
                finish()
            }else{
                createToast(getString(R.string.errorDatabase))
            }
        })
    }

    private fun createNewHero() {
        if (checkHeroInput()) {
            val imageUrl = if (binding.checkBox.isChecked) {
                getString(R.string.randomImage)
            } else {
                binding.textImageUrl.text.toString()
            }

            val formatter = DateTimeFormatter.ofPattern(getString(R.string.datePattern))
            val date = LocalDate.parse(binding.heroDate.text, formatter)

            val createdSuperHero = SuperHero(
                0,
                binding.textName.text.toString(),
                binding.textDescription.text.toString(),
                imageUrl,
                date,
                comicsList,
                seriesList
            )

            addHeroViewModel.addHero(createdSuperHero)
            finish()
        }
    }

    private fun checkHeroInput(): Boolean {
        var check = false
        with(binding) {
            if (textName.text.isEmpty() || textName.text.isBlank()) {
                createToast(getString(R.string.nameEmptyError))
            } else if (textDescription.text.isEmpty() || textDescription.text.isBlank()) {
                createToast(getString(R.string.descriptionEmptyError))
            } else if ((textImageUrl.text.isEmpty() || textImageUrl.text.isBlank()) && !checkBox.isChecked) {
                createToast(getString(R.string.imageEmptyError))
            } else if (heroDate.text.isEmpty() || heroDate.text.isBlank()) {
                createToast(getString(R.string.emptyDateErrorHero))
            } else {
                check = true
            }
        }
        return check
    }

    private fun checkComicInput(): Boolean {
        var check = false
        with(binding) {
            if (nameComic2.text.isBlank() || nameComic2.text.isEmpty()) {
                createToast(getString(R.string.comisNameError))
            } else if (addComicDate.text.isBlank() || addComicDate.text.isEmpty()) {
                createToast(getString(R.string.comicDateError))
            } else {
                check = true
            }
        }
        return check
    }

    private fun createToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun clearComicText() {
        with(binding) {
            nameComic2.text.clear()
            addComicDate.text.clear()
        }
    }

    private fun createNewComic() {
        if (checkComicInput()) {
            val formatter = DateTimeFormatter.ofPattern(getString(R.string.datePattern))
            val adapter = binding.rvHeroComics.adapter as StringAdapter
            val createComic = Comic(
                0,
                binding.nameComic2.text.toString(),
                LocalDate.parse(binding.addComicDate.text.toString(), formatter)
            )
            comicsList.add(createComic)
            adapter.submitList(comicsList.map { it.toString() })
        }
    }

    private fun createNewSeries() {
        if (checkComicInput()) {
            val formatter = DateTimeFormatter.ofPattern(getString(R.string.datePattern))
            val createSeries = Series(
                0,
                binding.nameComic2.text.toString(),
                LocalDate.parse(binding.addComicDate.text.toString(), formatter)
            )
            seriesList.add(createSeries)
            seriesAdapter.submitList(seriesList.map { it.toString() })
        }
    }

    private fun showDatePickerDialog(currentText: TextView) {
        val newFragment = DatePickerFragment.newInstance { _, year, month, day ->
            val stringD = day.toString().padStart(2, '0')
            val stringM = month.toString().padStart(2, '0')
            val selectedDay = "${stringD}-${stringM}-${year}"
            currentText.text = selectedDay
        }
        newFragment.show(supportFragmentManager, getString(R.string.DatePickerName))
    }
}