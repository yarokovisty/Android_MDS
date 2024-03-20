package com.example.lab13.domain.usecases

import com.example.lab13.domain.entity.GameSettings
import com.example.lab13.domain.entity.Level
import com.example.lab13.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}