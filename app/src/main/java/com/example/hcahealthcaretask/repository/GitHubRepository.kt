package com.example.hcahealthcaretask.repository

import com.example.hcahealthcaretask.model.RepositoryDataItem
import com.example.hcahealthcaretask.service.GitHubApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

import javax.inject.Inject

class GitHubRepository @Inject constructor(private val apiService: GitHubApiService) {

    fun getRepositories(username: String, perPage: Int, page: Int): Single<List<RepositoryDataItem>> {
        return apiService.getRepositories(username, perPage, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}