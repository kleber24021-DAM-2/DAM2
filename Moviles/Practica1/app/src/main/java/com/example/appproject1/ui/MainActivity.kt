package com.example.appproject1.ui

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appproject1.R
import com.example.appproject1.domain.Person
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.switchmaterial.SwitchMaterial
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig

class MainActivity : AppCompatActivity() {

    private var personList: MutableList<Person> = mutableListOf()
    private var paginaActual: Int = 0

    private lateinit var nameText: TextView
    private lateinit var idText: TextView
    private lateinit var phoneText: TextView
    private lateinit var maleRadioButton: RadioButton
    private lateinit var isAdmin: SwitchMaterial
    private lateinit var genderGroup: RadioGroup
    private lateinit var counterText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Si existe un estado guardado, lo restablecemos
        if (savedInstanceState != null) {
            paginaActual = savedInstanceState.getInt("PAGINA_ACTUAL")
            personList.addAll(savedInstanceState.getParcelableArrayList<Person>("LIST_PERSON") as ArrayList<Person>)
        }

        nameText = findViewById(R.id.nameInputText)
        idText = findViewById(R.id.dniInputText)
        phoneText = findViewById(R.id.phoneInputText)
        maleRadioButton = findViewById(R.id.rbMan)
        isAdmin = findViewById(R.id.isAdminSwitch)
        genderGroup = findViewById(R.id.genderRadioGroup)
        counterText = findViewById(R.id.counterText)

        setListeners()

        //Función para añadir datos aleatorios. Cambiar el número para añadir más o menos datos
        addRandomData(25)
        updateDisplay()

    }

    private fun setListeners() {
        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            addPerson()
        }
        val eraseButton: Button = findViewById(R.id.eraseButton)
        eraseButton.setOnClickListener {
            removePerson()
        }
        val goRightButton: Button = findViewById(R.id.goRight)
        goRightButton.setOnClickListener {
            goRight()
        }
        val goLeftButton: Button = findViewById(R.id.goLeft)
        goLeftButton.setOnClickListener {
            goLeft()
        }
        val updateButton: Button = findViewById(R.id.updateButton)
        updateButton.setOnClickListener {
            updatePerson()
        }
        val goToRecyclerButton: Button = findViewById(R.id.listButton)
        goToRecyclerButton.setOnClickListener {
            goToList()
        }
    }

    private fun addPerson() {
        if (nameText.text.isBlank() || idText.text.isBlank() || phoneText.text.isBlank()) {
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        val person = Person(
            nameText.text.toString(),
            idText.text.toString(),
            phoneText.text.toString(),
            maleRadioButton.isChecked,
            isAdmin.isChecked,
        )
        personList.add(person)
        Toast.makeText(this, getString(R.string.toast_addPerson), Toast.LENGTH_SHORT).show()
        paginaActual = 0
        updateDisplay()
    }

    private fun removePerson() {
        if (paginaActual != 0) {
            paginaActual--
            Toast.makeText(this, getString(R.string.toast_RemovePerson), Toast.LENGTH_SHORT).show()
            updateDisplay()
            personList.removeAt(paginaActual)
        }
    }

    private fun updateDisplay() {
        val goRightButton: Button = findViewById(R.id.goRight)
        val goLeftButton: Button = findViewById(R.id.goLeft)
        val eraseButton: Button = findViewById(R.id.eraseButton)
        val updateButton: Button = findViewById(R.id.updateButton)

        when {
            personList.isEmpty() -> {
                goRightButton.isEnabled = false
                goLeftButton.isEnabled = false
                eraseButton.isEnabled = false
                updateButton.isEnabled = false
            }
            paginaActual == personList.size -> {
                goRightButton.isEnabled = false
                goLeftButton.isEnabled = true
            }
            paginaActual == 0 -> {
                eraseButton.isEnabled = false
                updateButton.isEnabled = false
                goLeftButton.isEnabled = false
                goRightButton.isEnabled = true
            }
            else -> {
                goLeftButton.isEnabled = true
                goRightButton.isEnabled = true
            }
        }

        if (paginaActual == 0) {
            nameText.text = ""
            idText.text = ""
            phoneText.text = ""
            genderGroup.clearCheck()
            isAdmin.isChecked = false
            counterText.text = getString(R.string.label_NewPerson)
        } else {
            eraseButton.isEnabled = true
            updateButton.isEnabled = true
            val actualPerson: Person = personList[paginaActual - 1]
            nameText.text = actualPerson.name
            idText.text = actualPerson.dni
            phoneText.text = actualPerson.telephone
            when (actualPerson.isMale) {
                true -> {
                    genderGroup.check(R.id.rbMan)
                }
                false -> {
                    genderGroup.check(R.id.rbWoman)
                }
            }
            isAdmin.isChecked = actualPerson.isAdmin
            counterText.text = (paginaActual).toString() + " / " + (personList.size).toString()
        }
        val progressBar: LinearProgressIndicator = findViewById(R.id.linearProgressIndicator)
        progressBar.max = personList.size
        progressBar.progress = paginaActual
    }

    private fun updatePerson() {
        val person = Person(
            nameText.text.toString(),
            idText.text.toString(),
            phoneText.text.toString(),
            maleRadioButton.isChecked,
            isAdmin.isChecked,
        )
        personList[paginaActual - 1] = person
        Toast.makeText(this, getString(R.string.toast_UpdatePerson), Toast.LENGTH_SHORT).show()
        updateDisplay()
    }

    private fun goRight() {
        if (paginaActual < personList.size) {
            paginaActual++
        }
        updateDisplay()
    }

    private fun goLeft() {
        if (paginaActual > 0) {
            paginaActual--
        }
        updateDisplay()
    }

    private fun goToList() {
        val intent = Intent(this, PersonDisplay::class.java)
        intent.putExtra(getString(R.string.intentName), personList as ArrayList)
        startActivity(intent)
    }

    private fun addRandomData(nDataToAdd: Int) {
        val config = fakerConfig {
            locale = "es"
        }
        val faker = Faker(config)
        for (i in 0..nDataToAdd) {
            val isMale = faker.gender.shortBinaryTypes() == "f"
            val isAdmin = faker.programmingLanguage.name() == "Java"
            val person = Person(
                faker.name.name(),
                faker.idNumber.invalid(),
                faker.phoneNumber.cellPhone(),
                isMale,
                isAdmin
            )
            personList.add(person)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("PAGINA_ACTUAL", paginaActual)
        outState.putParcelableArrayList("LIST_PERSON", java.util.ArrayList<Person>(personList))
    }
}