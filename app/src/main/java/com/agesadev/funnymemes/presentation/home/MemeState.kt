package com.agesadev.funnymemes.presentation.home

import com.agesadev.funnymemes.presentation.model.MemePresentation


data class MemeState(
    val isLoading: Boolean = false,
    val data: List<MemePresentation> = emptyList(),
    val error: String = ""

)