package com.frakton.moviesapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication : Application() {
    fun getAppContext(): Context {
        return applicationContext
    }
}