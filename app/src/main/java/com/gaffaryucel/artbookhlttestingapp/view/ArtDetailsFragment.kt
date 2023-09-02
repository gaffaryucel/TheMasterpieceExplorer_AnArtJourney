package com.gaffaryucel.artbookhlttestingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentArtDetailsBinding
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.viewmodel.ArtDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtDetailsFragment : Fragment() {

    private lateinit var viewModel: ArtDetailsViewModel
    private lateinit var binding: FragmentArtDetailsBinding
    private var url : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArtDetailsViewModel::class.java)
        resultListener()
        binding.artImageView.setOnClickListener{
            val action = ArtDetailsFragmentDirections.actionArtDetailsFragment3ToSearchImageFragment2()
            Navigation.findNavController(it).navigate(action)
        }
        binding.saveButton.setOnClickListener{
            val name = binding.nameText.text.toString()
            val artist = binding.artistText.text.toString()
            val year = binding.dateText.text.toString()
            val art = ArtModel(name,artist,year,url ?: "",null)
            viewModel.insertArt(art)
        }
    }
    private fun resultListener(){
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            url = bundle.getString("url")
            Glide.with(requireContext()).load(url).into(binding.artImageView)
        }
    }
}