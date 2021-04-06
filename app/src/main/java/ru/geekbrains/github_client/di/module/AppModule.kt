package ru.geekbrains.github_client.di.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.github_client.ui.App

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app
}