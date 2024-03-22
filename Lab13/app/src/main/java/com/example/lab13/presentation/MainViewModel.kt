package com.example.lab13.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab13.data.RepositoryCityImpl
import com.example.lab13.domain.entity.City
import com.example.lab13.domain.usercases.GetCityUserCases

class MainViewModel: ViewModel() {
    private val repository = RepositoryCityImpl

    private val getCityUserCases = GetCityUserCases(repository)

    private val _cityItem = MutableLiveData<City>()
    val cityItem: LiveData<City>
        get() = _cityItem

    fun getCityItem(cityId: Int) {
        val item = getCityUserCases(cityId)
        _cityItem.value = item
    }
}