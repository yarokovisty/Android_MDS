package com.example.lab13.domain.repository

import com.example.lab13.domain.entity.GameSettings
import com.example.lab13.domain.entity.Level
import com.example.lab13.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings
}