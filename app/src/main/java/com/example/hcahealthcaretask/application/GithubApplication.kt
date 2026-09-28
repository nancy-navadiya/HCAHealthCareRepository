package com.example.hcahealthcaretask.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
/** Application entry point that enables Hilt dependency injection. */
class GithubApplication : Application()