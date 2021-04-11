package ru.geekbrains.github_client.di.module

import android.widget.ImageView
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import ru.geekbrains.github_client.mvp.model.api.IDataSource
import ru.geekbrains.github_client.mvp.model.cache.IGithubRepositoriesCache
import ru.geekbrains.github_client.mvp.model.cache.IGithubUsersCache
import ru.geekbrains.github_client.mvp.model.cache.IImageCache
import ru.geekbrains.github_client.mvp.model.image.IImageLoader
import ru.geekbrains.github_client.mvp.model.network.INetworkStatus
import ru.geekbrains.github_client.mvp.model.repository.IGithubRepositoriesRepo
import ru.geekbrains.github_client.mvp.model.repository.IGithubUsersRepo
import ru.geekbrains.github_client.mvp.model.repository.RetrofitGithubRepositoriesRepo
import ru.geekbrains.github_client.mvp.model.repository.RetrofitGithubUsersRepo
import ru.geekbrains.github_client.ui.image.GlideImageLoader
import javax.inject.Named
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

    @Singleton
    @Provides
    fun repositoriesRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IGithubRepositoriesCache
    ): IGithubRepositoriesRepo = RetrofitGithubRepositoriesRepo(api, networkStatus, cache)

    @Singleton
    @Provides
    fun imagesRepo(
        networkStatus: INetworkStatus,
        cache: IImageCache,
        @Named("ui") uiScheduler: Scheduler
    ): IImageLoader<ImageView> =
        GlideImageLoader(networkStatus, cache, uiScheduler)
}