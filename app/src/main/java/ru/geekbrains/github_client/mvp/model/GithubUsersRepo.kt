package ru.geekbrains.github_client.mvp.model

import io.reactivex.rxjava3.core.Observable
import ru.geekbrains.github_client.mvp.model.entity.GithubUser

class GithubUsersRepo {
    private val users = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    fun getUsers(): Observable<GithubUser> = Observable.fromIterable(users)
}