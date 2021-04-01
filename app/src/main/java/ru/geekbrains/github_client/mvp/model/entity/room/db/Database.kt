package ru.geekbrains.github_client.mvp.model.entity.room.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.geekbrains.github_client.mvp.model.entity.room.RoomCachedImage
import ru.geekbrains.github_client.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.github_client.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.github_client.mvp.model.entity.room.dao.IImageDao
import ru.geekbrains.github_client.mvp.model.entity.room.dao.IRepositoryDao
import ru.geekbrains.github_client.mvp.model.entity.room.dao.IUserDao

@androidx.room.Database(
    entities = [
        RoomGithubUser::class,
        RoomGithubRepository::class,
        RoomCachedImage::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val userDao: IUserDao
    abstract val repositoryDao: IRepositoryDao
    abstract val imageDao: IImageDao

    companion object {
        private const val DB_NAME = "database.db"
        private var instance: Database? = null
        fun getInstance() = instance ?: throw IllegalStateException("Database has not been created")
        fun create(context: Context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, Database::class.java, DB_NAME).build()
            }
        }
    }
}
