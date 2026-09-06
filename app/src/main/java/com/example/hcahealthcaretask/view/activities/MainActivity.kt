package com.example.hcahealthcaretask.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.hcahealthcaretask.R
import com.example.hcahealthcaretask.application.GithubApplication
import com.example.hcahealthcaretask.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //UseFull while integrating dagger
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as GithubApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationGraph()
    }

    // Ensure the back button works correctly with the NavController
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_github_nav)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //set navigation controller and start destination
    private fun setNavigationGraph() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_github_nav) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.github_nav_graph)
        navGraph.setStartDestination(R.id.repoListFragment)

        navController.graph = navGraph

    }
}