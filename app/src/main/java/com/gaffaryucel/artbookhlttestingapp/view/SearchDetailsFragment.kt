package com.gaffaryucel.artbookhlttestingapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gaffaryucel.artbookhlttestingapp.adapter.ArtAdapter
import com.gaffaryucel.artbookhlttestingapp.databinding.SearchResultDetailsBinding
import com.gaffaryucel.artbookhlttestingapp.viewmodel.SearchResultDetailsViewModel

class SearchDetailsFragment : Fragment() {
    private lateinit var binding: SearchResultDetailsBinding
    private lateinit var viewModel: SearchResultDetailsViewModel
    private lateinit var adapter : ArtAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchResultDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchResultDetailsViewModel::class.java)
        val title = arguments?.getString("t")
        val description = arguments?.getString("d")
        if (description.isNullOrEmpty()){
            binding.contentTextView.visibility = View.GONE
        }
        binding.titleTextView.text = title
        binding.contentTextView.text = description
        binding.webview.webViewClient = MyWebViewClient()
        var lasttext = ""
        if (title!!.contains("Period")){
            lasttext = title.substringBefore("Period")
        }else{
            val changedText = title.replace(" ","_")
            lasttext = changedText
        }
        binding.webview.loadUrl("https://en.wikipedia.org/wiki/$lasttext")
    }
    inner class MyWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.webViewProgressBar.visibility = View.GONE
        }
    }
}