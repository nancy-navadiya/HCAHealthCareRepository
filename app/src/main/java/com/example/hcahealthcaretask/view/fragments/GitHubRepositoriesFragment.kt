package com.example.hcahealthcaretask.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hcahealthcaretask.R
import com.example.hcahealthcaretask.application.GithubApplication
import com.example.hcahealthcaretask.databinding.FragmentRepositoriesBinding
import com.example.hcahealthcaretask.model.RepositoryDataItem
import com.example.hcahealthcaretask.utils.Constants
import com.example.hcahealthcaretask.view.adapters.GitHubRepositoriesAdapter
import com.example.hcahealthcaretask.viewmodels.GitHubRepositoriesViewModel
import com.google.gson.Gson
import javax.inject.Inject

class GitHubRepositoriesFragment : Fragment(R.layout.fragment_repositories) {

    private lateinit var viewModel: GitHubRepositoriesViewModel
    private lateinit var binding: FragmentRepositoriesBinding
    private lateinit var adapter: GitHubRepositoriesAdapter
    private lateinit var layoutManager: LinearLayoutManager
    var isDataFiltered = false

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoriesBinding.bind(view)

        (activity?.application as GithubApplication).appComponent.inject(this)

        setVewModelForGithubRepo()

        setRecyclerview(view)
        seterror()

        // fetch initial data
        viewModel.fetchRepositories(Constants.DEFAULT_USERNAME , true)

        searchRepository()
        paginationOfRepositories()
        filterRepositoriesBySearch()

    }

    private fun setVewModelForGithubRepo() {
        viewModel = ViewModelProvider(this, viewModelFactory)[GitHubRepositoriesViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setRecyclerview(view: View) {
        //adapter initialization and onclick item managed
        adapter = GitHubRepositoriesAdapter { repo ->
            val jsonString = Gson().toJson(repo)
            val bundle = Bundle()
            bundle.putString("repository", jsonString)
            findNavController(view).navigate(
                R.id.action_repoListFragment_to_repoDetailFragment,
                bundle
            )
        }

        //set adapter to recyclerview
        binding.repositoriesRecyclerView.adapter = adapter

        //set layoutManager to recyclerview
        layoutManager = LinearLayoutManager(requireContext())
        binding.repositoriesRecyclerView.layoutManager = layoutManager
        binding.repositoriesRecyclerView.adapter = adapter

        //handle data  found or not and update ui accordingly
        viewModel.repositories.observe(viewLifecycleOwner) { repositories ->
            checkRepoListIsEmptyOrNot(repositories, Constants.NO_DATA_MSG)
        }

        // Observe filtered repositories
        viewModel.filteredRepositories.observe(viewLifecycleOwner, Observer { repositories ->
            checkRepoListIsEmptyOrNot(repositories, Constants.NO_DATA_LANG)
        })
    }

    private fun searchRepository() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val username = s.toString()
                viewModel.currentPage = 1
                viewModel.isLastPage = false
                viewModel.isLoading = false
                viewModel.clearRepositoryList()
                if (username.isNotEmpty()) {
                    viewModel.fetchRepositories(username, true) // Call the API on text change
                } else {
                    viewModel.fetchRepositories(Constants.DEFAULT_USERNAME , true)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    //Handled pagination
    private fun paginationOfRepositories() {

        binding.repositoriesRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLoading && !viewModel.isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        // End of the list is reached, fetch more data
                        if (!isDataFiltered) {
                            if (binding.searchBar.textEditSearch().isNotEmpty()) {
                                viewModel.fetchRepositories(binding.searchBar.textEditSearch(), false)
                            } else {
                                viewModel.fetchRepositories(Constants.DEFAULT_USERNAME , false)
                            }
                        }
                    }
                }
            }
        })
    }

    // Show error message
    private fun seterror() {
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            binding.repositoriesRecyclerView.visibility = View.GONE
            binding.textError.visibility = View.VISIBLE
            binding.textError.text = Constants.ERROR_MSG
        }
    }

    // Handle language filter selection
    private fun filterRepositoriesBySearch() {
        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedLanguage = parent?.getItemAtPosition(position).toString()
                    isDataFiltered = selectedLanguage != "All"
                    viewModel.clearFilteredRepositoryList()
                    viewModel.filterRepositories(selectedLanguage)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    fun checkRepoListIsEmptyOrNot(repositories: List<RepositoryDataItem>, message: String) {
        if (repositories.isEmpty()) {
            binding.repositoriesRecyclerView.visibility = View.GONE
            binding.textError.visibility = View.VISIBLE
            binding.textError.text = message
        } else {
            binding.repositoriesRecyclerView.visibility = View.VISIBLE
            binding.textError.visibility = View.GONE
            adapter.submitList(repositories)
        }
    }
}