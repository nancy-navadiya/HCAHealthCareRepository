package com.example.hcahealthcaretask.component

import com.example.hcahealthcaretask.module.NetworkModule
import com.example.hcahealthcaretask.module.ViewModelModule
import com.example.hcahealthcaretask.view.activities.MainActivity
import com.example.hcahealthcaretask.view.fragments.GitHubRepositoriesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: GitHubRepositoriesFragment)
}
