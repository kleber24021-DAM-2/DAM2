package com.example.appproject1.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject1.R
import com.example.appproject1.domain.Person

class PersonAdapter(private val personList: List<Person>) :
    RecyclerView.Adapter<PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PersonViewHolder(layoutInflater.inflate(R.layout.person_item, parent, false))
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.render(personList[position])
    }

    override fun getItemCount(): Int {
        return personList.size
    }

}

class PersonViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun render(person: Person) {
        view.findViewById<TextView>(R.id.tvName).text = person.name
        view.findViewById<TextView>(R.id.tvDni).text = person.dni
        view.findViewById<TextView>(R.id.tvPhone).text = person.telephone
        view.findViewById<TextView>(R.id.tvAdmin).text =
            if (person.isAdmin) "Es admin" else "No es admin"
        view.findViewById<TextView>(R.id.tvGender).text = if (person.isMale) "Hombre" else "Mujer"
    }
}