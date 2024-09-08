package com.example.lab23.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lab23.R
import com.example.lab23.databinding.ActivityMainBinding
import com.example.lab23.prensentation.GithubViewModel
import com.example.lab23.utils.getQueryTextChangeStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[GithubViewModel::class.java]
    }
    private lateinit var adapter: RepositoryListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
        setChangeText()
        collectViewModel()
    }

    private fun setRecyclerView() {
        adapter = RepositoryListAdapter()
        binding.rvRepositories.adapter = adapter

        setOnClickListener()
    }

    private fun setOnClickListener() {
        adapter.setOnClickRepository = { repository ->
            navigateToRepository(repository.htmlUrl)
        }
    }

    private fun setChangeText() {
        binding.searchRepositories.getQueryTextChangeStateFlow()
            .debounce(300)
            .filter { query ->
                if (query.isBlank()) {
                    adapter.submitList(emptyList())
                    return@filter false
                } else {
                    return@filter true
                }
            }
            .distinctUntilChanged()
            .onEach { viewModel.search(it) }
            .launchIn(lifecycleScope)
    }

    private fun collectViewModel() {
        lifecycleScope.launch {
            viewModel.searchResults.collect { repositories ->
                adapter.submitList(repositories)
            }
        }
    }

    private fun navigateToRepository(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

}