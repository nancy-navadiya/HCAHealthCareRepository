package com.example.hcahealthcaretask.application

import android.app.Application
import com.example.hcahealthcaretask.component.ApplicationComponent
import com.example.hcahealthcaretask.component.DaggerApplicationComponent

class GithubApplication : Application(){

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder().build()
    }
}