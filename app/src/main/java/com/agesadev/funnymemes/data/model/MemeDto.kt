package com.agesadev.funnymemes.data.model

import com.google.firebase.firestore.FieldPath.documentId

data class MemeDto(
    val id: String = documentId().toString(),
    val name: String? = null,
    val url: String? = null
)

