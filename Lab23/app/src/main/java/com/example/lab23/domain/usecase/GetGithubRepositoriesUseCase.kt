package com.example.lab23.domain.usecase

import com.example.lab23.domain.entity.GithubItem
import com.example.lab23.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

class GetGithubRepositoriesUseCase(private val repository: GithubRepository) {

    operator fun invoke(repositoryName: String): Flow<List<GithubItem>> =
        repository.getGithubRepositories(repositoryName)
}