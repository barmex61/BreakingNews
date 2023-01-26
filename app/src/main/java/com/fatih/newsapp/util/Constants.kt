package com.fatih.newsapp.util

import java.util.*

object Constants {

    const val API_KEY="f64e8d0c7b9d42eca6eb63bbfaea19ac"
    const val START_PAGE=1
    const val BASE_URL="https://newsapi.org/"
    const val SEARCH_NEWS_TIME_DELAY=500L

    init {
        val countries= hashMapOf<String,String>()
        val countryCodes= Locale.getISOCountries()
        for(country in countryCodes){
            countries[Locale("",country).displayCountry] = country.toLowerCase()
        }
    }
}