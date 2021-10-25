package com.example.crudfromjson.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudfromjson.R
import com.example.crudfromjson.databinding.HeroDisplayBinding
import com.example.crudfromjson.domain.ownmodels.Hero

class HeroAdapter(private val heroList: List<Hero>, private val buttonAction: ButtonAction) : RecyclerView.Adapter<HeroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HeroViewHolder(layoutInflater.inflate(R.layout.hero_display, parent, false))
    }

    interface ButtonAction{
        fun onClickButton(nombre:String)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.render(heroList[position], buttonAction)
    }

    override fun getItemCount(): Int {
        return heroList.size
    }

}

class HeroViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = HeroDisplayBinding.bind(view)
    fun render(hero: Hero,
               buttonAction: HeroAdapter.ButtonAction) {
        with(binding){
            heroTextView.text = hero.toString()
            binding.button.setOnClickListener{
                buttonAction.onClickButton(heroTextView.text.toString())
            }
        }
    }


}