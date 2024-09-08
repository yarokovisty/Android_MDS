package com.example.lab23.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.lab23.domain.entity.GithubItem

class RepositoryDiffCallback : DiffUtil.ItemCallback<GithubItem>() {
    override fun areItemsTheSame(oldItem: GithubItem, newItem: GithubItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubItem, newItem: GithubItem): Boolean {
        return oldItem == newItem
    }
}