package ru.geekbrains.github_client.di

import dagger.Component
import ru.geekbrains.github_client.di.module.*
import ru.geekbrains.github_client.mvp.presenter.MainPresenter
import ru.geekbrains.github_client.mvp.presenter.RepositoryPresenter
import ru.geekbrains.github_client.mvp.presenter.UserPresenter
import ru.geekbrains.github_client.mvp.presenter.UsersPresenter
import ru.geekbrains.github_client.ui.activity.MainActivity
import ru.geekbrains.github_client.ui.adapter.UsersRVAdapter
import ru.geekbrains.github_client.ui.fragment.UserFragment
import ru.geekbrains.github_client.ui.fragment.UsersFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        ApiModule::class,
        RepoModule::class,
        CacheModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(usersFragment: UsersFragment)
    fun inject(userFragment: UserFragment)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
    fun inject(usersRVAdapter: UsersRVAdapter)
}