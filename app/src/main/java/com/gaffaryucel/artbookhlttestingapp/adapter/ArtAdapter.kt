package com.gaffaryucel.artbookhlttestingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.gaffaryucel.artbookhlttestingapp.databinding.RowArtBinding
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.Hit
import javax.inject.Inject

class ArtAdapter @Inject constructor(
    val glide: RequestManager
): RecyclerView.Adapter<ArtAdapter.ArtViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<ArtModel>(){
        override fun areItemsTheSame(oldItem: ArtModel, newItem: ArtModel): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: ArtModel, newItem: ArtModel): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var artList : List<ArtModel>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    inner class ArtViewHolder(val binding: RowArtBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val binding = RowArtBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val myart = artList[position]
        holder.binding.apply {
            art = myart
            glide.load(myart.imageUrl).into(artImage)
        }
    }

    override fun getItemCount(): Int {
        return artList.size
    }
}
