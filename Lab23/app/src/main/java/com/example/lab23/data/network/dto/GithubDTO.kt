package com.example.lab23.data.network.dto

import com.google.gson.annotations.SerializedName

data class GithubDTO(
    val id: Long,
    val name: String,
    val owner: GithubOwnerDTO,
    val language: String?,
    val description: String?,
    @SerializedName("html_url")
    val htmlUrl: String
)
