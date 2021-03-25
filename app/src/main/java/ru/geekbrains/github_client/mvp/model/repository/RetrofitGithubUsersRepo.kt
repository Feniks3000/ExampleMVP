package ru.geekbrains.github_client.mvp.model.repository

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.github_client.mvp.model.api.IDataSource

class RetrofitGithubUsersRepo(val api: IDataSource) : IGithubUsersRepo {
    override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
}