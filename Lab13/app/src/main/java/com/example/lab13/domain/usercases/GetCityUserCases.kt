package com.example.lab13.domain.usercases

import com.example.lab13.data.RepositoryCityImpl
import com.example.lab13.domain.entity.City

class GetCityUserCases(private val repository: RepositoryCityImpl) {
    operator fun invoke(cityId: Int): City {
        return repository.cities[cityId]
    }
}