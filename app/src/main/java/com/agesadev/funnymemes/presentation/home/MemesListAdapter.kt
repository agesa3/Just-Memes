package com.agesadev.funnymemes.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agesadev.funnymemes.R
import com.agesadev.funnymemes.databinding.SingleMemeImgBinding
import com.agesadev.funnymemes.domain.model.MemeDomain
import com.agesadev.funnymemes.presentation.model.MemePresentation
import com.agesadev.funnymemes.util.ItemClick
import com.bumptech.glide.Glide

class MemesListAdapter : ListAdapter<MemePresentation, MemesListAdapter.MemeViewHolder>(memeDiffUtil) {

    inner class MemeViewHolder(private val binding: SingleMemeImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(memeDomain: MemePresentation) {
            binding.apply {
                Glide
                    .with(itemView)
                    .load(memeDomain.imageurl)
                    .placeholder(R.drawable.meme_placeholder)
                    .into(memeImage)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        return MemeViewHolder(
            SingleMemeImgBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val expert = getItem(position)
        holder.bind(expert)
    }
}

val memeDiffUtil = object : DiffUtil.ItemCallback<MemePresentation>() {
    override fun areItemsTheSame(oldItem: MemePresentation, newItem: MemePresentation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MemePresentation, newItem: MemePresentation): Boolean {
        return oldItem == newItem
    }

}