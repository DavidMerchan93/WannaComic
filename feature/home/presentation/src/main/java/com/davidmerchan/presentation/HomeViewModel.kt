package com.davidmerchan.presentation

/*@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeComicsRepository
): ViewModel() {

    fun getComics() {
        viewModelScope.launch {

            when(val result = homeRepository.getComicsHome()) {
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
}*/
