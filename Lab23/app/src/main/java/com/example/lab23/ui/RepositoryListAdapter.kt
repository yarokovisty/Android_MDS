package com.example.lab23.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab23.R
import com.example.lab23.domain.entity.GithubItem

class RepositoryListAdapter :
    ListAdapter<GithubItem, RepositoryViewHolder>(RepositoryDiffCallback()) {
    var setOnClickRepository: ((GithubItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            setOnClickRepository?.invoke(item)
        }
    }
}