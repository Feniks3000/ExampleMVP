package ru.geekbrains.github_client.mvp.model.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository

interface IDataSource {
    @GET("users")
    fun getUsers(): Single<List<GithubUser>>

    @GET
    fun getRepositories(@Url url: String) : Single<List<GithubRepository>>
}