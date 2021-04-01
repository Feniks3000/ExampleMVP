package ru.geekbrains.github_client.mvp.model.repository

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.github_client.mvp.model.api.IDataSource
import ru.geekbrains.github_client.mvp.model.cache.IGithubUsersCache
import ru.geekbrains.github_client.mvp.model.network.INetworkStatus

class RetrofitGithubUsersRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val cache: IGithubUsersCache
) : IGithubUsersRepo {

    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers()
                .flatMap { users ->
                    cache.putUsers(users).toSingleDefault(users)
                }
        } else {
            cache.getUsers()
        }
    }.subscribeOn(Schedulers.io())
}