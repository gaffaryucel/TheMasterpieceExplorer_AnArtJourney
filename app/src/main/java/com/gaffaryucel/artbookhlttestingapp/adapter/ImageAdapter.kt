package com.gaffaryucel.artbookhlttestingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaffaryucel.artbookhlttestingapp.databinding.RowArtBinding
import com.gaffaryucel.artbookhlttestingapp.databinding.RowItemBinding
import com.gaffaryucel.artbookhlttestingapp.databinding.WorkOfArtistRowBinding
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.Hit
import com.gaffaryucel.artbookhlttestingapp.model.PixabeyModel

class ImageAdapter (private val urlListener : UrlListener) :  RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Hit>(){
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var artList : List<Hit>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    inner class ImageViewHolder(val binding: WorkOfArtistRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = WorkOfArtistRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = artList[position]
        Glide.with(holder.itemView.context).load(image.largeImageURL).into(holder.binding.workofArtistImageView)
        holder.itemView.setOnClickListener{
            urlListener.getUrl(image.previewURL)
        }
    }

    override fun getItemCount(): Int {
        return artList.size
    }
}
