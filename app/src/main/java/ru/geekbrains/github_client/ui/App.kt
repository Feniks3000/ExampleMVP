package ru.geekbrains.github_client.ui

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import ru.geekbrains.github_client.di.AppComponent
import ru.geekbrains.github_client.di.DaggerAppComponent
import ru.geekbrains.github_client.di.module.AppModule
import ru.geekbrains.github_client.mvp.model.entity.room.db.Database

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        Database.create(this)

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}