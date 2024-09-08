package com.example.lab23.data.network.api

import com.example.lab23.data.network.dto.ResultGithub
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun getRepositories(@Query("q") query: String): ResultGithub
}