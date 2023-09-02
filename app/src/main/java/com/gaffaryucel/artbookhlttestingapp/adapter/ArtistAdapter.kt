package com.gaffaryucel.artbookhlttestingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.gaffaryucel.artbookhlttestingapp.databinding.RowArtBinding
import com.gaffaryucel.artbookhlttestingapp.databinding.RowArtistBinding
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.ui.notifications.NotificationsFragmentDirections
import javax.inject.Inject

class ArtistAdapter @Inject constructor(
    val glide: RequestManager
) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

        private val diffUtil = object : DiffUtil.ItemCallback<Artist>(){
            override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                return oldItem == newItem
            }
        }
        private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

        var artList : List<Artist>
            get() = recyclerListDiffer.currentList
            set(value) = recyclerListDiffer.submitList(value)

        inner class ArtistViewHolder(val binding: RowArtistBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
            val binding = RowArtistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ArtistViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
            val myartist = artList[position]
            holder.binding.apply {
                artist = myartist
                Glide.with(holder.itemView.context).load(myartist.image).into(artistImageView)
            }
            holder.itemView.setOnClickListener {
                val action = NotificationsFragmentDirections.actionNavigationNotificationsToWorkOfArtistFragment(myartist.url,myartist.image,myartist.artistName)
                Navigation.findNavController(it).navigate(action)
            }
        }

        override fun getItemCount(): Int {
            return artList.size
        }
}