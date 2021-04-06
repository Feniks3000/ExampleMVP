package ru.geekbrains.github_client.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.geekbrains.github_client.mvp.model.cache.IGithubRepositoriesCache
import ru.geekbrains.github_client.mvp.model.cache.IGithubUsersCache
import ru.geekbrains.github_client.mvp.model.cache.IImageCache
import ru.geekbrains.github_client.mvp.model.cache.room.RoomGithubRepositoriesCache
import ru.geekbrains.github_client.mvp.model.cache.room.RoomGithubUsersCache
import ru.geekbrains.github_client.mvp.model.cache.room.RoomImageCache
import ru.geekbrains.github_client.mvp.model.entity.room.db.Database
import ru.geekbrains.github_client.ui.App
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: App) =
        Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()

    @Singleton
    @Provides
    fun usersCache(db: Database): IGithubUsersCache = RoomGithubUsersCache(db)

    @Singleton
    @Provides
    fun repositoriesCache(db: Database): IGithubRepositoriesCache = RoomGithubRepositoriesCache(db)

    @Singleton
    @Named("cacheDir")
    @Provides
    fun cacheDir(app: App): File = app.cacheDir

    @Singleton
    @Provides
    fun imagesCache(database: Database, @Named("cacheDir") cacheDir: File): IImageCache =
        RoomImageCache(database, cacheDir)
}