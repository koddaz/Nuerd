package com.example.nuerd.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class Country(
    @SerializedName("name") val name: Name? = null,
    @SerializedName("cca2") val cca2: String? = null,
    @SerializedName("flags") val flags: Flags? = null
)

data class Name(
    @SerializedName("common") val common: String? = null
)

data class Flags(
    @SerializedName("png") val png: String? = null
)

interface CountryRepository {
    @GET("v3.1/all")
    suspend fun getCountries(): List<Country>
}

open class getCountriesViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/") // Ensure the baseUrl ends with a '/'
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val countryApiService = retrofit.create(CountryRepository::class.java)

    private val _countries = MutableLiveData<List<Country>>()
    var countries: LiveData<List<Country>> = _countries


    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countriesList = countryApiService.getCountries()
                val sortedCountriesList = countriesList.sortedBy { it.name?.common }
                _countries.postValue(sortedCountriesList)
                Log.d("Countries", "Countries loaded: $countriesList")
            } catch (e: Exception) {
                Log.e("getCountriesViewModel", "Error loading countries", e)
            }
        }
    }
}