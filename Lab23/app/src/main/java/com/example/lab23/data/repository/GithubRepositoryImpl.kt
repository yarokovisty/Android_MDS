package com.example.lab23.data.repository

import com.example.lab23.data.mapper.GithubMapper
import com.example.lab23.data.network.api.GithubService
import com.example.lab23.domain.entity.GithubItem
import com.example.lab23.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GithubRepositoryImpl(
    private val service: GithubService,
    private val mapper: GithubMapper
) : GithubRepository {

    override fun getGithubRepositories(repositoryName: String): Flow<List<GithubItem>> =
        flow {
            val repositories = service.getRepositories(repositoryName).items.map {
                mapper.mapGithubDTOToGithubItem(it)
            }
            emit(repositories)
        }
}