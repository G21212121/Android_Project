package com.boulderingapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BoulderingApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}