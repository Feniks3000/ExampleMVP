package ru.geekbrains.github_client.mvp.navigation

import com.github.terrakok.cicerone.Screen
import ru.geekbrains.github_client.mvp.model.entity.GithubUser

interface IScreens {
    fun users(): Screen
    fun user(user: GithubUser): Screen
}