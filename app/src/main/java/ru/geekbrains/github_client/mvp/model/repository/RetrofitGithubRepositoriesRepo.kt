package ru.geekbrains.github_client.mvp.model.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.github_client.mvp.model.api.IDataSource
import ru.geekbrains.github_client.mvp.model.cache.IGithubRepositoriesCache
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.network.INetworkStatus

class RetrofitGithubRepositoriesRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val cache: IGithubRepositoriesCache
) : IGithubRepositoriesRepo {

    override fun getRepositories(user: GithubUser) =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getRepositories(url).flatMap { repositories ->
                        cache.putUserRepositories(user, repositories).toSingleDefault(repositories)
                    }
                } ?: Single.error<List<GithubRepository>>(RuntimeException("User with empty reposUrl"))
                    .subscribeOn(Schedulers.io())
            } else {
                cache.getUserRepositories(user)
            }
        }.subscribeOn(Schedulers.io())
}