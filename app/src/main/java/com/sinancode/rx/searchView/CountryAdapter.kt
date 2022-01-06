package com.sinancode.rx.searchView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.sinancode.rx.R

class CountryAdapter(
    val context: Context,
    var countries: List<Country>,
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewCountryName = itemView.findViewById<TextView>(R.id.text_view_country_name)
        val textViewCountryCapital = itemView.findViewById<TextView>(R.id.text_view_country_capital)
        fun bindProduct(country: Country,position: Int) {
            textViewCountryName.text = country.name
            textViewCountryCapital.text = country.capital
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_country, parent, false)
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindProduct(countries[position],position)
    }
    override fun getItemCount(): Int = countries.size
}