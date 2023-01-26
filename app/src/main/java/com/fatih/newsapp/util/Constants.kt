package com.fatih.newsapp.util

import java.util.*

object Constants {

    const val API_KEY="52e58260607f4d63a6c7f822f0ca7a4c"
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