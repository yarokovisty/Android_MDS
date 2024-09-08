package com.example.lab23.prensentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab23.data.mapper.GithubMapper
import com.example.lab23.data.network.api.ServiceFactory
import com.example.lab23.data.repository.GithubRepositoryImpl
import com.example.lab23.domain.entity.GithubItem
import com.example.lab23.domain.usecase.GetGithubRepositoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class GithubViewModel : ViewModel() {

    private val service = ServiceFactory
    private val mapper = GithubMapper()
    private val repository = GithubRepositoryImpl(service.githubService, mapper)

    private val getGithubRepositoriesUseCase = GetGithubRepositoriesUseCase(repository)

    private val _searchResults = MutableStateFlow<List<GithubItem>>(emptyList())
    val searchResults: StateFlow<List<GithubItem>> get() = _searchResults.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            getGithubRepositoriesUseCase(query)
                .flowOn(Dispatchers.IO)
                .catch {
                    _searchResults.value = emptyList()
                }
                .flowOn(Dispatchers.Default)
                .collect {
                    _searchResults.value = it
                }
        }
    }
}