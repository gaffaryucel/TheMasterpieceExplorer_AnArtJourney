package com.gaffaryucel.artbookhlttestingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentArtDetailsBinding
import com.gaffaryucel.artbookhlttestingapp.model.ArtModel
import com.gaffaryucel.artbookhlttestingapp.util.Status
import com.gaffaryucel.artbookhlttestingapp.viewmodel.ArtDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment  : Fragment() {

    private lateinit var viewModel: ArtDetailsViewModel
    private lateinit var binding: FragmentArtDetailsBinding
    private var url : String? = null
    private var artId : Int? = null
    private var currentName = ""
    private var currentArtistName = ""
    private var currentDate = ""
    private var currentUrl = ""

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
        idListener()
        binding.artImageView.setOnClickListener{
            val action = ArtDetailsFragmentDirections.actionArtDetailsFragment2ToSearchImageFragment()
            Navigation.findNavController(it).navigate(action)
        }
        var i = 0
        binding.saveButton.setOnClickListener{
            i += 1
            val name = binding.nameText.text.toString()
            val artist = binding.artistText.text.toString()
            val year = binding.dateText.text.toString()
            if (artId != null){
                if (name.isNotEmpty()){
                    currentName = name
                }
                if (artist.isNotEmpty()){
                    currentArtistName = artist
                }
                if (year.isNotEmpty()){
                    currentDate = year
                }
                viewModel.updateArt(currentName,currentArtistName,currentDate,currentUrl,artId ?: 0)
                runBlocking {
                    binding.artDetailsProgressBar.visibility = View.VISIBLE
                    delay(500)
                    findNavController().popBackStack()
                }

            }else{
                viewModel.makeArt(name,artist,year,url ?: "")
                runBlocking {
                    binding.artDetailsProgressBar.visibility = View.VISIBLE
                    delay(500)
                    findNavController().popBackStack()
                }
            }
        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.arts.observe(viewLifecycleOwner, Observer {
            it.forEach{ artModel ->
                if (artModel.id == artId){
                    currentName = artModel.artName
                    currentArtistName = artModel.artistName
                    currentDate = artModel.date
                    currentUrl = artModel.imageUrl
                    binding.nameText.setHint(currentName)
                    binding.artistText.setHint(currentArtistName)
                    binding.dateText.setHint(currentDate)
                    Glide.with(requireContext()).load(currentUrl).into(binding.artImageView)
                }
            }
        })
        viewModel.message.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    binding.artDetailsProgressBar.visibility = View.INVISIBLE
                    binding.artDetailsErrorText.visibility = View.INVISIBLE
                }
                Status.LOADING->{
                    binding.artDetailsProgressBar.visibility = View.VISIBLE
                    binding.artDetailsErrorText.visibility = View.INVISIBLE
                }
                Status.ERROR->{
                    binding.artDetailsProgressBar.visibility = View.INVISIBLE
                    binding.artDetailsErrorText.visibility = View.VISIBLE
                }
            }
        })
    }
    private fun resultListener(){
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            url = bundle.getString("url")
            Glide.with(requireContext()).load(url).into(binding.artImageView)
        }
    }
    private fun idListener(){
        setFragmentResultListener("requestKey2") { requestKey, bundle ->
            val id = bundle.getString("id")
            val response = try {
                id?.toInt()
            }catch (e : Exception){
                -0
            }
            if (response != null && response != -0){
                artId = response
            }
        }
    }
}