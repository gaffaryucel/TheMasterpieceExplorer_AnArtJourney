package com.gaffaryucel.artbookhlttestingapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.gaffaryucel.artbookhlttestingapp.databinding.LayoutArtistDetailsBinding
import com.gaffaryucel.artbookhlttestingapp.viewmodel.ArtistDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtistDetailsFragment : Fragment() {

    private lateinit var binding: LayoutArtistDetailsBinding
    private lateinit var viewModel: ArtistDetailsViewModel
    private lateinit var url : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutArtistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArtistDetailsViewModel::class.java)

        val artist = arguments?.getString("artist")
        viewModel.getArtistInfo(artist.toString())

        observeLiveData()

        binding.wikiButton.setOnClickListener { // İstediğiniz URL'yi buraya girin
            val newurl = url.replace("/en/","/tr/")
            binding.webView.settings.javaScriptEnabled = true // Eğer JavaScript kullanacaksanız bu özelliği aktifleştirin
            binding.webView.webViewClient = WebViewClient()
            binding.webView.loadUrl(newurl)
        }
    }
    private fun observeLiveData() {
        viewModel.artistData.observe(viewLifecycleOwner, Observer { artistInfo ->
            val cleanedText = removeUrls(artistInfo.biography)
            url = artistInfo.wikipediaUrl
            binding.apply {
                artist = artistInfo
            }
            Glide.with(requireContext()).load(artistInfo.image).into(binding.artistImageView)
            binding.biographyTextView.text = cleanedText
        })
    }
    fun removeUrls(inputText: String): String {
        // URL'leri tanımlayan düzenli ifade
        val urlPattern = "(https?|ftp)://[^\\s/$.?#].[^\\s]*"

        // Düzenli ifadeyi aramak için Regex kullanma
        val regex = Regex(urlPattern)

        // Eşleşen URL'leri boşlukla değiştirme
        val cleanedText = regex.replace(inputText, "")

        return cleanedText
    }
}