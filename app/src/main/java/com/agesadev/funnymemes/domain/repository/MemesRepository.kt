package com.agesadev.funnymemes.domain.repository

import com.agesadev.funnymemes.domain.model.MemeDomain
import com.agesadev.funnymemes.util.Resource
import kotlinx.coroutines.flow.Flow

interface MemesRepository {

    fun getMemesFromFireStore(): Flow<Resource<List<MemeDomain>>>
}