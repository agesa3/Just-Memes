package com.agesadev.funnymemes.domain.usecases

import com.agesadev.funnymemes.domain.repository.MemesRepository
import javax.inject.Inject

class GetMemesUseCase @Inject constructor(
    private val memesRepository: MemesRepository
) {
    operator fun invoke() = memesRepository.getMemesFromFireStore()
}