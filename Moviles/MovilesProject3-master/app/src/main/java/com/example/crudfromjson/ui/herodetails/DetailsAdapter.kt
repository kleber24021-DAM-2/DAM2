package com.example.crudfromjson.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudfromjson.R
import com.example.crudfromjson.databinding.StringDisplayBinding

class DetailsAdapter(private val stringList: List<String>) : RecyclerView.Adapter<StringHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StringHolder(layoutInflater.inflate(R.layout.string_display, parent, false))
    }

    override fun onBindViewHolder(holder: StringHolder, position: Int) {
        holder.render(stringList[position])
    }

    override fun getItemCount(): Int {
        return stringList.size
    }

}

class StringHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = StringDisplayBinding.bind(view)
    fun render(string: String) {
        binding.textView2.text = string
    }
}