package com.gaffaryucel.artbookhlttestingapp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.HiltTestApplication


class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}