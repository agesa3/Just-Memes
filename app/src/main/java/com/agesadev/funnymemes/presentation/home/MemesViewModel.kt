package com.agesadev.funnymemes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agesadev.funnymemes.domain.model.MemeDomain
import com.agesadev.funnymemes.domain.usecases.GetMemesUseCase
import com.agesadev.funnymemes.presentation.mappers.toPresentationModel
import com.agesadev.funnymemes.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemesViewModel @Inject constructor(
    private val memesUseCase: GetMemesUseCase
) : ViewModel() {

    private val _memes = MutableStateFlow(MemeState())
    val meme: StateFlow<MemeState> = _memes

    init {
        getAllMemes()
    }

    private fun getAllMemes() {
        viewModelScope.launch {
            memesUseCase().onStart {
                _memes.value = MemeState(isLoading = true)
            }.collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _memes.value =
                            MemeState(data = result.data?.map { it.toPresentationModel() }
                                ?: emptyList())
                    }
                    is Resource.Error -> {
                        _memes.value = MemeState(error = result.error.toString())
                    }
                    is Resource.Loading -> {
                        _memes.value = MemeState(isLoading = true)
                    }
                }
            }
        }
    }
}