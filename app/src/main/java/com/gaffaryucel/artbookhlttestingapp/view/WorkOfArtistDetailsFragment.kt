package com.gaffaryucel.artbookhlttestingapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gaffaryucel.artbookhlttestingapp.R
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentWorkOfArtistDetailsBinding
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.viewmodel.WorkOfArtistDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkOfArtistDetailsFragment : Fragment() {

    private lateinit var viewModel: WorkOfArtistDetailsViewModel
    private lateinit var binding : FragmentWorkOfArtistDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkOfArtistDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorkOfArtistDetailsViewModel::class.java)
        val contentId = arguments?.getString("id")?.let{
            viewModel.getWorkOfArtist(it)
            println(it)
        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.workOfArtist.observe(viewLifecycleOwner, Observer {
            binding.apply {
                artDetails = it
            }
            println(it.description)
            Glide.with(requireContext()).load(it.image).into(binding.artDetailsImageView)
        })
    }
}