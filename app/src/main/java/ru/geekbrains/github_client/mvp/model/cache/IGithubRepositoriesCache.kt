package ru.geekbrains.github_client.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.model.entity.GithubUser

interface IGithubRepositoriesCache {
    fun getUserRepositories(user: GithubUser): Single<List<GithubRepository>>
    fun putUserRepositories(user: GithubUser, repositories: List<GithubRepository>): Completable
}