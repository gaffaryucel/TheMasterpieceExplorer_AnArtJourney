package com.gaffaryucel.artbookhlttestingapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gaffaryucel.artbookhlttestingapp.databinding.SearchItemRowBinding
import com.gaffaryucel.artbookhlttestingapp.model.SearchModel
import com.gaffaryucel.artbookhlttestingapp.view.SearchArtFragmentDirections

class SearchAdapter (val list : List<SearchModel>): RecyclerView.Adapter<SearchAdapter.SearchHolder>() {
    class SearchHolder(val binding : SearchItemRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding = SearchItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchHolder(binding)

    }
    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item = list[position]
        if (item.imageUrl != null){
            Glide.with(holder.itemView.context).load(item.imageUrl).into(holder.binding.imageView)
        }else{
            holder.binding.imageView.setImageResource(item.image)
        }
        holder.binding.nameText.text = item.name
        holder.itemView.setOnClickListener{
            val action = SearchArtFragmentDirections.actionSearchArtFragmentToSearchDetailsFragment2(item.name,"")
            Navigation.findNavController(it).navigate(action)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }


}