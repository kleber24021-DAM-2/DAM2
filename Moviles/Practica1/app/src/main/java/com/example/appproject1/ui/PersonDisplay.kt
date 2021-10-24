package com.example.appproject1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject1.R
import com.example.appproject1.domain.Person

class PersonDisplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_display)

        val listPerson = intent.getParcelableArrayListExtra<Person>(getString(R.string.intentName))

        val rvPerson = this.findViewById<RecyclerView>(R.id.rvPerson)

        rvPerson.setHasFixedSize(true)
        rvPerson.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        rvPerson.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


        listPerson?.let {
            rvPerson.adapter = PersonAdapter(it)
            rvPerson.layoutManager = GridLayoutManager(this@PersonDisplay, 2)
        }
    }

}