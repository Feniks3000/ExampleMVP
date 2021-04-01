package ru.geekbrains.github_client.mvp.model.repository

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.github_client.mvp.model.entity.GithubRepo

interface IGithubRepositoriesRepo {
    fun getUserRepos(url: String): Single<List<GithubRepo>>
}