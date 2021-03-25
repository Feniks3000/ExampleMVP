package ru.geekbrains.github_client.mvp.model.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import ru.geekbrains.github_client.mvp.model.entity.GithubUser

interface IDataSource {

    @GET("users")
    fun getUsers(): Single<List<GithubUser>>

//    @GET
//    fun getUserRepos(@Url url: String) : Single

}