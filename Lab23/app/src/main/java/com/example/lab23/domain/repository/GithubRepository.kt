package com.example.lab23.domain.repository

import com.example.lab23.domain.entity.GithubItem
import kotlinx.coroutines.flow.Flow


interface GithubRepository {

    fun getGithubRepositories(repositoryName: String): Flow<List<GithubItem>>
}