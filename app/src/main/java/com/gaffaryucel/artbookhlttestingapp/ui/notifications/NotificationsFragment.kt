package com.gaffaryucel.artbookhlttestingapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gaffaryucel.artbookhlttestingapp.adapter.ArtistAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.HomePageAdapter
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentNotificationsBinding
import com.gaffaryucel.artbookhlttestingapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment @Inject constructor(
    private val adapter : ArtistAdapter,
    private val homePageAdapter : HomePageAdapter
): Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var viewModel : NotificationsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        binding.storiesRecyclerview.layoutManager = GridLayoutManager(requireContext(),1,
            LinearLayoutManager.HORIZONTAL,false)
        binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        observeLiveData()
    }
    private fun observeLiveData() {
        viewModel.artistListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.artList = it
            binding.storiesRecyclerview.adapter = adapter
            adapter.notifyDataSetChanged()
        })
        viewModel.homePageItems.observe(viewLifecycleOwner, Observer {
            homePageAdapter.artList = it
            binding.postsRecyclerView.adapter = homePageAdapter
            homePageAdapter.notifyDataSetChanged()
        })
        viewModel.message.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    binding.storiesRecyclerview.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING->{
                    binding.storiesRecyclerview.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR->{
                    binding.storiesRecyclerview.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}