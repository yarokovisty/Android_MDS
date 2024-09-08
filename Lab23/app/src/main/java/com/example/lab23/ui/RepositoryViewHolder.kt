package com.example.lab23.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lab23.databinding.ItemRepositoryBinding
import com.example.lab23.domain.entity.GithubItem

class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemRepositoryBinding.bind(view)

    fun bind(repositoryItem: GithubItem) = with(binding) {
        tvNameProjectValue.text = repositoryItem.name
        tvAuthorProjectValue.text = repositoryItem.author
        tvProgrammingLanguageValue.text = repositoryItem.language
        tvDescription.text = repositoryItem.description
    }
}