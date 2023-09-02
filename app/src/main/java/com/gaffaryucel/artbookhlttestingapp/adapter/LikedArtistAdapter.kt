package com.gaffaryucel.artbookhlttestingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.gaffaryucel.artbookhlttestingapp.databinding.WorkOfArtistRowBinding
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.FirebaseArtistModel
import com.gaffaryucel.artbookhlttestingapp.ui.home.HomeFragmentDirections
import com.gaffaryucel.artbookhlttestingapp.view.WorkOfArtistFragmentDirections
import javax.inject.Inject

class LikedArtistAdapter @Inject constructor(
    private val glide : RequestManager
) : RecyclerView.Adapter<LikedArtistAdapter.ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<FirebaseArtistModel>(){
        override fun areItemsTheSame(
            oldItem: FirebaseArtistModel,
            newItem: FirebaseArtistModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: FirebaseArtistModel,
            newItem: FirebaseArtistModel
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var artistList : List<FirebaseArtistModel>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    inner class ViewHolder(val binding: WorkOfArtistRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WorkOfArtistRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artist = artistList[position]
        glide.load(artist.image)
            .into(holder.binding.workofArtistImageView)
        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections.actionNavigationHomeToWorkOfArtistFragment(artist.url?:"",artist.image?:"",artist.name?:"")
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return artistList.size
    }
}