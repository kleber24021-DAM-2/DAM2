package com.example.crudfromjson.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.crudfromjson.R
import com.example.crudfromjson.databinding.HeroDisplayBinding
import com.example.crudfromjson.domain.ownmodels.SuperHero

class HeroAdapter(private val superHeroList: List<SuperHero>, private val buttonAction: ButtonAction) : RecyclerView.Adapter<HeroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HeroViewHolder(layoutInflater.inflate(R.layout.hero_display, parent, false))
    }

    interface ButtonAction{
        fun onClickEyeButton(id:Int)
        fun onClickEraseButton(superHero: SuperHero)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.render(superHeroList[position], buttonAction)
    }

    override fun getItemCount(): Int {
        return superHeroList.size
    }

}

class HeroViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = HeroDisplayBinding.bind(view)
    fun render(superHero: SuperHero,
               buttonAction: HeroAdapter.ButtonAction) {
        with(binding){
            heroTextView.text = superHero.toString()
            imageView.load(superHero.imageUrl)
            binding.button.setOnClickListener{
                buttonAction.onClickEyeButton(superHero.id)
            }
            binding.eraseButton.setOnClickListener{
                buttonAction.onClickEraseButton(superHero)
            }
        }
    }


}