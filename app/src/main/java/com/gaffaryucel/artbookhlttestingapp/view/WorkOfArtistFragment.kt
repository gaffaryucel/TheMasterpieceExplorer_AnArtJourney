package com.gaffaryucel.artbookhlttestingapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.gaffaryucel.artbookhlttestingapp.R
import com.gaffaryucel.artbookhlttestingapp.adapter.WorkOfArtistAdapter
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentWorkOfArtistBinding
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.FirebaseArtistModel
import com.gaffaryucel.artbookhlttestingapp.viewmodel.WorkOfArtistViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WorkOfArtistFragment @Inject constructor(
    private val adapter : WorkOfArtistAdapter
) : Fragment() {

    private lateinit var viewModel: WorkOfArtistViewModel
    private lateinit var binding : FragmentWorkOfArtistBinding
    private var artist =""
    private var image  = ""
    private var name   =""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkOfArtistBinding.inflate(inflater,container,false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkOfArtistViewModel::class.java)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        artist = arguments?.getString("url").toString()
        image = arguments?.getString("img").toString()
        name =  arguments?.getString("name").toString()
        image.let {
            if (it.isEmpty()){
                GlobalScope.launch {
                    viewModel.getArtistPhoto(artist)
                }
            }
        }
        Glide.with(requireContext()).load(image).into(binding.toolbarImageView)
        binding.collapsingToolbar.title = name
        viewModel.getWorkOfArtist(artist)
        binding.toolbarImageView.setOnClickListener{
            val action =  WorkOfArtistFragmentDirections.actionWorkOfArtistFragmentToArtistDetailsFragment(artist)
            Navigation.findNavController(it).navigate(action)
        }
        binding.likeFab.setOnClickListener{
            viewModel.saveLikedArtist(FirebaseArtistModel(name,image,artist))
        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.workOfArtistLD.observe(viewLifecycleOwner, Observer {
            adapter.workOfArtist = it as ArrayList<Art>
            binding.recyclerView.adapter =adapter
        })
        viewModel.artistImageLd.observe(viewLifecycleOwner, Observer {
            Glide.with(requireContext()).load(it).into(binding.toolbarImageView)
            image = it
        })
    }
}