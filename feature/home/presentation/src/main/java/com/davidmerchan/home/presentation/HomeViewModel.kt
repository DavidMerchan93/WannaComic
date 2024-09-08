package com.davidmerchan.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidmerchan.core.model.Resource
import com.davidmerchan.domain.repository.HomeComicsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeComicsRepository
): ViewModel() {

    fun getComics() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                homeRepository.getComicsHome()
            }
            when(result) {
                Resource.Loading -> {
                    println("Loading")
                }
                is Resource.Error -> {
                    println(result.message)
                }
                is Resource.Success -> {
                    println(result.result.name)
                }
            }

        }
    }
}
