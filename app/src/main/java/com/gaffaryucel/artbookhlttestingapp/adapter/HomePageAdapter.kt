package com.gaffaryucel.artbookhlttestingapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.gaffaryucel.artbookhlttestingapp.R
import com.gaffaryucel.artbookhlttestingapp.databinding.HomePageRowBinding
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.HomePageModel
import com.gaffaryucel.artbookhlttestingapp.repo.WikiArtApiRepoInterface
import com.gaffaryucel.artbookhlttestingapp.ui.notifications.NotificationsFragmentDirections
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.gaffaryucel.artbookhlttestingapp.view.SearchArtFragmentDirections
import com.gaffaryucel.artbookhlttestingapp.viewmodel.WorkOfArtistViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HomePageAdapter @Inject constructor(
    val glide: RequestManager,
    val repo : WikiArtApiRepoInterface
) : RecyclerView.Adapter<HomePageAdapter.ViewHolder>() {

    private val disposable = CompositeDisposable()

    private val diffUtil = object : DiffUtil.ItemCallback<HomePageModel>(){
        override fun areItemsTheSame(oldItem: HomePageModel, newItem: HomePageModel): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: HomePageModel, newItem: HomePageModel): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var artList : List<HomePageModel>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    inner class ViewHolder(val binding : HomePageRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomePageRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sanatEseri = artList[position]
        glide.load(sanatEseri.image)
            .into(holder.binding.artImageView)
        holder.binding.detailsButton.setOnClickListener{
            val title_for_send = sanatEseri.title.replace(" ","_")
            val action = NotificationsFragmentDirections.actionNavigationNotificationsToSearchDetailsFragment(title_for_send,"")
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.apply {
            image = sanatEseri
        }
        val url = sanatEseri.artistName.lowercase().replace(" ","-")
        GlobalScope.launch {
            getArtistInfo(holder,url)
        }
        holder.binding.idLLTopBar.setOnClickListener{
            val action = NotificationsFragmentDirections.actionNavigationNotificationsToWorkOfArtistFragment(url,"",sanatEseri.artistName)
            Navigation.findNavController(it).navigate(action)
        }
    }
    override fun getItemCount(): Int {
        return artList.size
    }
    suspend fun getArtistInfo(holder : ViewHolder,artist: String) = coroutineScope {
        launch {
            val single = repo.getArtistDetails(artist)
            disposable.add(
                single
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        response?.let {
                            glide.load(it.image).into(holder.binding.ArtistImageView)
                        }
                    }, { error ->
                        println(error.localizedMessage)

                    })
            )
        }
    }
}
