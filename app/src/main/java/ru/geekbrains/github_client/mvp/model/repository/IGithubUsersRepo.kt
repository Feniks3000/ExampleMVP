package ru.geekbrains.github_client.mvp.model.repository

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.github_client.mvp.model.entity.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}