package com.gaffaryucel.artbookhlttestingapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaffaryucel.artbookhlttestingapp.adapter.ArtAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.IdListener
import com.gaffaryucel.artbookhlttestingapp.adapter.IdListenerFr
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentArtListBinding
import com.gaffaryucel.artbookhlttestingapp.util.Status
import com.gaffaryucel.artbookhlttestingapp.viewmodel.ArtListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtListFragment : Fragment() , IdListener {

    private lateinit var adapter : ArtAdapter
    private lateinit var binding: FragmentArtListBinding
    private lateinit var viewModel: ArtListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtListBinding.inflate(inflater, container, false)
        adapter = ArtAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArtListViewModel::class.java)
        binding.artsRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        binding.editProfilePhoto.setOnClickListener {
            val action = ArtListFragmentDirections.actionNavigationArtsToEditProfileFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.swipeRefreshLayoutProfile.setOnRefreshListener {
            binding.swipeRefreshLayoutProfile.isRefreshing = false
            viewModel.getUserData()
        }
        observeLiveData()
    }
    private fun observeLiveData() {
        viewModel.arts.observe(viewLifecycleOwner, Observer {
            adapter.artList = it
            binding.artsRecyclerView.adapter = adapter
        })
        viewModel.userData.observe(viewLifecycleOwner, Observer {
            binding.apply {
                user = it
            }
            binding.userNicknameText.text = "@${it.email?.substringBefore("@")}"
        })
        viewModel.message.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    binding.swipeRefreshLayoutProfile.visibility = View.VISIBLE
                    binding.profileLayoutProgressBar.visibility = View.INVISIBLE
                    binding.profileLayoutErrorMessage.visibility = View.INVISIBLE
                }
                Status.LOADING->{
                    binding.swipeRefreshLayoutProfile.visibility = View.INVISIBLE
                    binding.profileLayoutProgressBar.visibility = View.VISIBLE
                    binding.profileLayoutErrorMessage.visibility = View.INVISIBLE
                }
                Status.ERROR->{
                    binding.swipeRefreshLayoutProfile.visibility = View.INVISIBLE
                    binding.profileLayoutProgressBar.visibility = View.INVISIBLE
                    binding.profileLayoutErrorMessage.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun getId(id: String,view : View) {
        setFragmentResult("requestKey2", bundleOf("id" to id))
        val action = ArtListFragmentDirections.actionNavigationArtsToNavigationCreate()
        Navigation.findNavController(view).navigate(action)
    }
}
