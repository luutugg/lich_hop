package com.example.lichhop.presentation

import android.app.Application

class LichHopApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }
}

private var application: Application? = null

private fun setApplication(app: Application){
    application = app
}

fun getApplication(): Application{
    return application ?: throw Exception("application null")
}
