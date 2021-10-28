package com.example.crudfromjson.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.crudfromjson.R
import com.example.crudfromjson.databinding.HeroDisplayBinding
import com.example.crudfromjson.domain.ownmodels.SuperHero
import java.time.LocalDate

class HeroAdapter(private val superHeroList: List<SuperHero>, private val buttonAction: ButtonAction) : RecyclerView.Adapter<HeroViewHolder>(), Filterable{
    var heroFilterList = ArrayList<SuperHero>()
    init{
        heroFilterList = superHeroList as ArrayList<SuperHero>
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HeroViewHolder(layoutInflater.inflate(R.layout.hero_display, parent, false))
    }

    interface ButtonAction{
        fun onClickEyeButton(id:Int)
        fun onClickEraseButton(superHero: SuperHero)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.render(heroFilterList[position], buttonAction)
    }

    override fun getItemCount(): Int {
        return heroFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                heroFilterList = if (charSearch.isBlank() || charSearch.isEmpty()){
                    superHeroList as ArrayList<SuperHero>
                }else{
                    val resultList = ArrayList<SuperHero>()
                    for (row in superHeroList){
                        if(row.name.lowercase().startsWith(p0.toString().lowercase())){
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = heroFilterList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

                heroFilterList = p1?.values as ArrayList<SuperHero>
                notifyDataSetChanged()
            }

        }
    }

//    fun filterByDate(startDate: LocalDate, endDate: LocalDate){
//        heroFilterList = superHeroList.filter {
//
//        }
//    }

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