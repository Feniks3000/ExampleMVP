package ru.geekbrains.github_client.ui.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.navigation.IScreens
import ru.geekbrains.github_client.ui.fragment.RepositoryFragment
import ru.geekbrains.github_client.ui.fragment.UserFragment
import ru.geekbrains.github_client.ui.fragment.UsersFragment

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(user: GithubUser) = FragmentScreen { UserFragment.newInstance(user) }
    override fun repository(githubRepository: GithubRepository) = FragmentScreen { RepositoryFragment.newInstance(githubRepository) }
}