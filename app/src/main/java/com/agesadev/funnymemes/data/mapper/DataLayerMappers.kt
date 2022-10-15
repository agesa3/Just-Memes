package com.agesadev.funnymemes.data.mapper

import com.agesadev.funnymemes.data.model.MemeDto
import com.agesadev.funnymemes.domain.model.MemeDomain

fun MemeDto.toDomainModel(): MemeDomain {
    return MemeDomain(
        id = id,
        name = name ?: "",
        imageurl = url ?: ""
    )
}
