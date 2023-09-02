package com.gaffaryucel.artbookhlttestingapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaffaryucel.artbookhlttestingapp.adapter.ImageAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.UrlListener
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentSearchImageBinding
import com.gaffaryucel.artbookhlttestingapp.viewmodel.ArtListViewModel
import com.gaffaryucel.artbookhlttestingapp.viewmodel.SearchImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchImageFragment : Fragment(), UrlListener {

    private lateinit var binding: FragmentSearchImageBinding
    private lateinit var viewModel: ArtListViewModel
    private lateinit var adapter : ImageAdapter
    private var imageUrl : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArtListViewModel::class.java)

        binding.searchEdittext.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            private var searchJob: Job? = null
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchJob?.cancel()
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    if(!query.isNullOrEmpty()) {
                        delay(1000)
                        viewModel.searchImage(query.toString())
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    if (!newText.isNullOrEmpty()) {
                        delay(1000)
                        viewModel.searchImage(newText.toString())
                    }
                }
                return true
            }

        })
        adapter = ImageAdapter(this)
        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(),3,LinearLayoutManager.VERTICAL,false)
        observeLiveData()
    }
    private fun observeLiveData() {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            if (it.data?.hits != null){
                adapter.artList = it.data.hits
            }
            binding.searchRecyclerView.adapter = adapter
        })
    }
    override fun getUrl(url: String) {
        setFragmentResult("requestKey", bundleOf("url" to url))
        findNavController().popBackStack()
    }
}