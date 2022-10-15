package com.agesadev.funnymemes.presentation.mappers

import com.agesadev.funnymemes.domain.model.MemeDomain
import com.agesadev.funnymemes.presentation.model.MemePresentation

fun MemeDomain.toPresentationModel(): MemePresentation {
    return MemePresentation(
        id = id,
        name = name,
        imageurl = imageurl
    )
}