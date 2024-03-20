package com.example.lab13.domain.entity

data class GameSettings(
    val maxSumValues: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
)