package ru.geekbrains.github_client.mvp.model.navigation

import com.github.terrakok.cicerone.Screen
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.model.entity.GithubUser

interface IScreens {
    fun users(): Screen
    fun user(user: GithubUser): Screen
    fun repository(githubRepository: GithubRepository): Screen
}