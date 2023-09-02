package com.gaffaryucel.artbookhlttestingapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.gaffaryucel.artbookhlttestingapp.adapter.LikedArtistAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.WorkOfArtistAdapter
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentHomeBinding
import com.gaffaryucel.artbookhlttestingapp.model.Art
import com.gaffaryucel.artbookhlttestingapp.model.FirebaseArtistModel
import javax.inject.Inject

class HomeFragment @Inject constructor(
    private val adapter : LikedArtistAdapter
) : Fragment() {

    private lateinit var viewModel : HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.likedArtistRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        observeLiveData()
    }
    private fun observeLiveData() {
        viewModel.artists.observe(viewLifecycleOwner, Observer {
            adapter.artistList = it
            binding.likedArtistRecyclerView.adapter =adapter
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}