package ru.geekbrains.github_client.mvp.model.repository

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.github_client.mvp.model.api.IDataSource

class RetrofitGithubRepositoriesRepo(val api: IDataSource) : IGithubRepositoriesRepo {
    override fun getUserRepos(url: String) = api.getUserRepos(url).subscribeOn(Schedulers.io())
}