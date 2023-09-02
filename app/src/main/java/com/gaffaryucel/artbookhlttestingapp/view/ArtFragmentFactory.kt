package com.gaffaryucel.artbookhlttestingapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.gaffaryucel.artbookhlttestingapp.adapter.ArtAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.ArtistAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.HomePageAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.LikedArtistAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.SearchAdapter
import com.gaffaryucel.artbookhlttestingapp.adapter.WorkOfArtistAdapter
import com.gaffaryucel.artbookhlttestingapp.ui.home.HomeFragment
import com.gaffaryucel.artbookhlttestingapp.ui.notifications.NotificationsFragment
import javax.inject.Inject

class ArtFragmentFactory@Inject constructor (
    val artAdapter: ArtAdapter,
    val workOfArtAdapter : WorkOfArtistAdapter,
    val artistAdapter : ArtistAdapter,
    val homePageAdapter : HomePageAdapter,
    val likedArtistAdapter : LikedArtistAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtListFragment::class.java.name->ArtListFragment(artAdapter)
            WorkOfArtistFragment::class.java.name->WorkOfArtistFragment(workOfArtAdapter)
            NotificationsFragment::class.java.name-> NotificationsFragment(artistAdapter,homePageAdapter)
            HomeFragment::class.java.name-> HomeFragment(likedArtistAdapter)
            else-> super.instantiate(classLoader, className)
        }
    }
}