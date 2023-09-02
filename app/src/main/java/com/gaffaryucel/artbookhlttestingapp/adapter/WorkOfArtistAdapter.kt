package com.gaffaryucel.artbookhlttestingapp.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gaffaryucel.artbookhlttestingapp.R
import com.gaffaryucel.artbookhlttestingapp.databinding.RowArtistBinding
import com.gaffaryucel.artbookhlttestingapp.databinding.WorkOfArtistRowBinding
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.ui.notifications.NotificationsFragmentDirections
import com.gaffaryucel.artbookhlttestingapp.view.WorkOfArtistFragmentDirections
import javax.inject.Inject

class WorkOfArtistAdapter @Inject constructor(
    private val glide : RequestManager
) : RecyclerView.Adapter<WorkOfArtistAdapter.WorkOfArtistViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Art>(){
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var workOfArtist : List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    inner class WorkOfArtistViewHolder(val binding: WorkOfArtistRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkOfArtistViewHolder {
        val binding = WorkOfArtistRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WorkOfArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkOfArtistViewHolder, position: Int) {
        val myArt = workOfArtist[position]
        glide.load(myArt.image)
            .into(holder.binding.workofArtistImageView)
        holder.itemView.setOnClickListener {
            val action = WorkOfArtistFragmentDirections.actionWorkOfArtistFragmentToWorkOfArtistDetailsFragment(myArt.contentId.toString())
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return workOfArtist.size
    }
}