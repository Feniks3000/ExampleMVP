package ru.geekbrains.github_client.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.geekbrains.github_client.mvp.model.cache.IGithubUsersCache
import ru.geekbrains.github_client.mvp.model.cache.room.RoomGithubUsersCache
import ru.geekbrains.github_client.mvp.model.entity.room.db.Database
import ru.geekbrains.github_client.ui.App
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: App) = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
        .build()

    @Singleton
    @Provides
    fun usersCahche(db: Database): IGithubUsersCache = RoomGithubUsersCache(db)

}