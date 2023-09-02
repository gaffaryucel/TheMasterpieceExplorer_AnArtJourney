package com.gaffaryucel.artbookhlttestingapp.adapter

import android.app.appsearch.SearchResults
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gaffaryucel.artbookhlttestingapp.databinding.SearchResultRowBinding
import com.gaffaryucel.artbookhlttestingapp.model.DataModel
import com.gaffaryucel.artbookhlttestingapp.view.SearchArtFragment
import com.gaffaryucel.artbookhlttestingapp.view.SearchArtFragmentDirections

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.SearchHolder>() {

    var resultList = ArrayList<DataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding  =SearchResultRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val data = resultList[position]
        holder.binding.titleTextView.text = data.title
        holder.binding.contentTextView.text = data.content
        holder.itemView.setOnClickListener{
            val action = SearchArtFragmentDirections.actionSearchArtFragmentToSearchDetailsFragment2(data.title,data.content)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return resultList.size
    }
    class SearchHolder(val binding : SearchResultRowBinding) : RecyclerView.ViewHolder(binding.root)
}