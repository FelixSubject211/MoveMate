package org.example.felix.kmp.movemate

import android.app.Application
import di.initKoin

class MoveMateApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}