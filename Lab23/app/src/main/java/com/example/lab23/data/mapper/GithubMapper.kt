package com.example.lab23.data.mapper

import com.example.lab23.data.network.dto.GithubDTO
import com.example.lab23.domain.entity.GithubItem

class GithubMapper {

    fun mapGithubDTOToGithubItem(githubDTO: GithubDTO): GithubItem =
        GithubItem(
            id = githubDTO.id,
            name = githubDTO.name,
            author = githubDTO.owner.login,
            language = githubDTO.language ?: "",
            description = githubDTO.description ?: "",
            htmlUrl = githubDTO.htmlUrl
        )
}