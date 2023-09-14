package com.gaffaryucel.artbookhlttestingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaffaryucel.artbookhlttestingapp.view.ArtFragmentFactory
import javax.inject.Inject

class ShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        supportActionBar?.hide()


    }
}