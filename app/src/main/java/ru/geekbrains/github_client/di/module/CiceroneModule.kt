package ru.geekbrains.github_client.di.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.geekbrains.github_client.mvp.model.navigation.IScreens
import ru.geekbrains.github_client.ui.navigation.AndroidScreens
import javax.inject.Singleton

@Module
class CiceroneModule {

    val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    fun navigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    fun router(): Router = cicerone.router

    @Singleton
    @Provides
    fun screens(): IScreens = AndroidScreens()
}