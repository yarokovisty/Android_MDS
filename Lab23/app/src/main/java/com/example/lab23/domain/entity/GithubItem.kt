package com.example.lab23.domain.entity

data class GithubItem(
    val id: Long,
    val name: String,
    val author: String,
    val language: String,
    val description: String,
    val htmlUrl: String
)