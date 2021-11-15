package com.example.roomproject.ui.updateheroactivity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.roomproject.R
import com.example.roomproject.databinding.ActivityUpdateHeroBinding
import com.example.roomproject.domain.SuperHero
import com.example.roomproject.ui.datePicker.DatePickerFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class UpdateHeroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateHeroBinding
    private val updateHeroViewModel: UpdateHeroViewModel by viewModels()
    private var heroId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        heroId = intent.getIntExtra(getString(R.string.heroIdExtra), -1)

        updateHeroViewModel.hero.observe(this, {
            with(binding) {
                textName.setText(it.name)
                textDescription.setText(it.description)
                textImageUrl.setText(it.imageUrl)
                heroDate.setText(it.modifiedDate.format(DateTimeFormatter.ofPattern(getString(R.string.datePattern))))
                reloadImage(it.imageUrl)
            }
        })

        updateHeroViewModel.getCompleteHero(heroId)

        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            textImageUrl.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    reloadImage(binding.textImageUrl.text.toString())
                }
            }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                binding.textImageUrl.isEnabled = !isChecked
                if (isChecked) {
                    reloadImage(getString(R.string.randomImage))
                } else {
                    reloadImage(binding.textImageUrl.text.toString())
                }
            }
            updateButton.setOnClickListener {

                updateHero()
            }
            heroDate.setOnClickListener {
                showDatePickerDialog(heroDate)
            }
        }
    }

    private fun updateHero() {
        if (checkHeroInput()) {
            val imageUrl: String = if (binding.checkBox.isChecked) {
                getString(R.string.randomImage)
            } else {
                binding.textImageUrl.text.toString()
            }

            val updateSuperHero = SuperHero(
                heroId,
                binding.textName.text.toString(),
                binding.textDescription.text.toString(),
                imageUrl,
                LocalDate.parse(
                    binding.heroDate.text.toString(),
                    DateTimeFormatter.ofPattern(getString(R.string.datePattern))
                ),
                emptyList(),
                emptyList()
            )
            updateHeroViewModel.updateHero(updateSuperHero)
            finish()
        }
    }

    private fun reloadImage(imageUrl: String) {
        binding.imageView2.load(imageUrl)
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

    private fun createToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
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
}