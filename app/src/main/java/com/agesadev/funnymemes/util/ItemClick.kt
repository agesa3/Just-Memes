package com.agesadev.funnymemes.util

import com.agesadev.funnymemes.domain.model.MemeDomain

interface ItemClick {
    fun onClickedMeme(memeDomain: MemeDomain)
}