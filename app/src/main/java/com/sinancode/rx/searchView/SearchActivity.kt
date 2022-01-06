package com.sinancode.rx.searchView

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import com.sinancode.rx.R
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class SearchActivity : AppCompatActivity() {

    lateinit var countries:ArrayList<Country>
    lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        prepareCountries()
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        countryAdapter = CountryAdapter(baseContext, arrayListOf())
        recyclerView.adapter = countryAdapter

        val queryObservable : Observable<CharSequence> = searchView.queryTextChanges()
        queryObservable
            .filter { query-> query.length >= 3 }
            .debounce (150,TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { queryToServer(it.toString()) }
    }

    private fun prepareCountries() {
        val jsonFileContent = application.assets.open("countries.json").bufferedReader().use {
            it.readText()
        }
        countries = Gson().fromJson(jsonFileContent, object : TypeToken<List<Country>>() {}.type)
    }


    fun queryToServer(query:String){
            val filtered = countries.filter {it.name.lowercase().contains(query)}
            setDataToRecyclerView(filtered)
    }

    fun setDataToRecyclerView(result:List<Country>){
        countryAdapter.countries = result
        countryAdapter.notifyDataSetChanged()
    }


}