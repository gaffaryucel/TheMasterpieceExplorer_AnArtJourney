package com.gaffaryucel.artbookhlttestingapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaffaryucel.artbookhlttestingapp.adapter.ArtAdapter
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentArtListBinding
import com.gaffaryucel.artbookhlttestingapp.viewmodel.ArtListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtListFragment @Inject constructor(
    private var adapter : ArtAdapter
): Fragment() {

    private lateinit var binding: FragmentArtListBinding
    private lateinit var viewModel: ArtListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArtListViewModel::class.java)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.button.setOnClickListener{
            val action = ArtListFragmentDirections.actionArtListFragment3ToArtDetailsFragment3()
            Navigation.findNavController(it).navigate(action)
        }
        observeLiveData()
    }
    private fun observeLiveData() {
        viewModel.arts.observe(viewLifecycleOwner, Observer {
            adapter.artList = it
            binding.recyclerView.adapter = adapter
        })
        viewModel.userData.observe(viewLifecycleOwner, Observer {
            binding.emailText.text = it.email
            binding.nameText.text = it.name
        })
    }
}
