package com.gaffaryucel.artbookhlttestingapp.view

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaffaryucel.artbookhlttestingapp.R
import com.gaffaryucel.artbookhlttestingapp.adapter.SearchAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.SearchResultAdapter
import com.gaffaryucel.artbookhlttestingapp.databinding.FragmentSearchBinding
import com.gaffaryucel.artbookhlttestingapp.model.Artist
import com.gaffaryucel.artbookhlttestingapp.model.DataModel
import com.gaffaryucel.artbookhlttestingapp.model.HomePageModel
import com.gaffaryucel.artbookhlttestingapp.model.SearchModel
import com.gaffaryucel.artbookhlttestingapp.viewmodel.SearchArtViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchArtFragment : Fragment() {

    private lateinit var viewModel: SearchArtViewModel
    private lateinit var binding : FragmentSearchBinding
    private val sockendAdapter = SearchResultAdapter()
    private var artistList = ArrayList<SearchModel>()
    private var artGenres = ArrayList<SearchModel>()
    private var myLastArtList = ArrayList<SearchModel>()
    private var chronologylist = ArrayList<SearchModel>()
    private var artList = ArrayList<SearchModel>()
    private var newsList = ArrayList<SearchModel>()
    private var tabTitles = ArrayList<Int>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchArtViewModel::class.java)
        observeliveData()
        binding.searchEdittext.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            private var searchJob: Job? = null
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchJob?.cancel()
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    if (query.isNullOrEmpty()) {
                        binding.resultRecyclerView.visibility = View.INVISIBLE
                        binding.recyclerView.visibility = View.VISIBLE
                    } else {
                        delay(1000)
                        viewModel.wikiClient(query.toString())
                        binding.resultRecyclerView.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.INVISIBLE
                    }
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    if (newText.isNullOrEmpty()) {
                        binding.resultRecyclerView.visibility = View.INVISIBLE
                        binding.recyclerView.visibility = View.VISIBLE
                    } else {
                        delay(1000)
                        viewModel.wikiClient(newText.toString())
                        binding.resultRecyclerView.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.INVISIBLE
                    }
                }
                return true
            }
        })

    }
    @SuppressLint("NotifyDataSetChanged")
    private fun observeliveData(){
        artList = ArrayList<SearchModel>()
        viewModel.resultList.observe(viewLifecycleOwner, Observer {
            sockendAdapter.resultList = it as ArrayList<DataModel>
            sockendAdapter.notifyDataSetChanged()
        })
        viewModel.artistListLiveData.observe(viewLifecycleOwner, Observer {
            artistList = convertList(it)
            binding.recyclerView.adapter = SearchAdapter(artistList)
        })
        viewModel.tabTitles.observe(viewLifecycleOwner, Observer {
            tabTitles = it
        })
        viewModel.artGenres.observe(viewLifecycleOwner, Observer {
            artGenres = it
        })
        viewModel.chronologylist.observe(viewLifecycleOwner, Observer {
            chronologylist = it
        })
        viewModel.artListLiveData.observe(viewLifecycleOwner, Observer {
            myLastArtList = convertListToArt(it)
        })
        viewModel.newsList.observe(viewLifecycleOwner, Observer {
            newsList = it
        })
        lifecycleScope.launch {
            println("s")
            // Wait for the current frame to finish rendering
            delay(1)
            // Call your next function here
            setupTabLayout(tabTitles)
            setUpAdapters()
        }
    }



    private fun setUpAdapters(){
        binding.resultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.resultRecyclerView.visibility = View.INVISIBLE
        binding.resultRecyclerView.adapter = sockendAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2) // İki sütunlu bir grid layout
    }
    private fun setupTabLayout(tabTitles : ArrayList<Int>) {
        binding.tabLayout.apply {
            // Her bir tabı oluştur ve başlığını ayarla
            for (i in tabTitles.indices) {
                val tab = newTab().setIcon(tabTitles[i])
                addTab(tab)
            }
            // Tab değişikliklerini dinle
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // Seçili tab değiştiğinde RecyclerView'ı güncelle
                    tab?.position?.let { position ->
                        setupRecyclerView(position)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // Implementasyon gerekmez
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // Implementasyon gerekmez
                }
            })
        }
    }
    private fun setupRecyclerView(position : Int) {
        binding.recyclerView.apply {
            when(position){
                0 -> {
                    adapter = SearchAdapter(artistList)
                }
                1 -> {
                    adapter = SearchAdapter(myLastArtList)
                }
                2 -> {
                    adapter = SearchAdapter(chronologylist)
                }
                3 -> {
                    adapter = SearchAdapter(artGenres)
                }
                4->{
                     adapter = SearchAdapter(newsList)
                }
            }
        }
    }
    private fun convertList(list : List<Artist>) : ArrayList<SearchModel>{
        val listInConverList = ArrayList<SearchModel>()
        list.forEach{
            val item = SearchModel(it.artistName,R.drawable.image,it.image)
            listInConverList.add(item)
        }
        return listInConverList
    }
    private fun convertListToArt(list: List<HomePageModel>?): ArrayList<SearchModel> {
        val listInconvertListToArt = ArrayList<SearchModel>()
        list?.forEach{ model ->
            val item = SearchModel(model.title,R.drawable.image,model.image)
            listInconvertListToArt.add(item)
        }
        return listInconvertListToArt
    }
}







