package com.agesadev.funnymemes.di

import com.agesadev.funnymemes.data.repository.MemesRepositoryImpl
import com.agesadev.funnymemes.domain.repository.MemesRepository
import com.agesadev.funnymemes.domain.usecases.GetMemesUseCase
import com.agesadev.funnymemes.domain.usecases.UseCases
import com.agesadev.funnymemes.util.Constants.MEME_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    fun provideFirestore() = Firebase.firestore

    @Provides
    fun memeRef(
        memeDb: FirebaseFirestore
    ) = memeDb.collection(MEME_COLLECTION)


    @Provides
    fun provideGetMemesUseCases(
        memesRepository: MemesRepository
    ) = UseCases(
        getMemesUseCase = GetMemesUseCase(memesRepository)
    )

    @Provides
    fun provideMemesRepository(
        memeRef: CollectionReference
    ):MemesRepository = MemesRepositoryImpl(memeRef)

}