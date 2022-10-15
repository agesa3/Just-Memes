package com.agesadev.funnymemes.data.repository

import com.agesadev.funnymemes.data.mapper.toDomainModel
import com.agesadev.funnymemes.data.model.MemeDto
import com.agesadev.funnymemes.domain.model.MemeDomain
import com.agesadev.funnymemes.domain.repository.MemesRepository
import com.agesadev.funnymemes.util.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MemesRepositoryImpl @Inject constructor(
    private val memeRef: CollectionReference
) : MemesRepository {

    override fun getMemesFromFireStore(): Flow<Resource<List<MemeDomain>>> = callbackFlow {
        val snapShotListener = memeRef.addSnapshotListener { snapshot, exception ->
            val memeResponse = if (snapshot != null && exception == null) {
                val memes = snapshot.toObjects(MemeDto::class.java)
                val memeList = memes.map {
                    it.toDomainModel()
                }
                Resource.Success(memeList)
            } else {
                Resource.Error(error = exception?.localizedMessage ?: "An Error Occurred")
            }
            trySend(memeResponse).isSuccess
        }
        awaitClose {
            snapShotListener.remove()
        }
    }
}