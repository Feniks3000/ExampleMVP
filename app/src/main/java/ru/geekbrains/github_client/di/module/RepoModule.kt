package ru.geekbrains.github_client.di.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.github_client.mvp.model.api.IDataSource
import ru.geekbrains.github_client.mvp.model.cache.IGithubUsersCache
import ru.geekbrains.github_client.mvp.model.network.INetworkStatus
import ru.geekbrains.github_client.mvp.model.repository.IGithubUsersRepo
import ru.geekbrains.github_client.mvp.model.repository.RetrofitGithubUsersRepo
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun usersRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IGithubUsersCache
    ): IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, cache)

}