package com.gaffaryucel.artbookhlttestingapp.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.gaffaryucel.artbookhlttestingapp.databinding.RowArtBinding
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.Hit
import com.gaffaryucel.artbookhlttestingapp.view.ArtDetailsFragment
import com.gaffaryucel.artbookhlttestingapp.view.ArtListFragmentDirections
import javax.inject.Inject

class ArtAdapter(
    val listener : IdListener
) : RecyclerView.Adapter<ArtAdapter.ArtViewHolder>() {

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
            Glide.with(holder.itemView.context).load(myart.imageUrl).into(artImageViewInProfileRecyclerView)
        }
        holder.itemView.setOnClickListener{
            listener.getId(myart.id.toString(),it)
        }
    }

    override fun getItemCount(): Int {
        return artList.size
    }
}
