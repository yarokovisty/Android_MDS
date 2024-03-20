package com.example.lab13.domain.entity

data class GameResult(
    val winner: Boolean,
    val countOfRightCounts: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
)