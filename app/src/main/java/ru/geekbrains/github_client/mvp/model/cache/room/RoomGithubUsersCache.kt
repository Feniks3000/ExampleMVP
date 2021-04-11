package ru.geekbrains.github_client.mvp.model.cache.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.github_client.mvp.model.cache.IGithubUsersCache
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.github_client.mvp.model.entity.room.db.Database

class RoomGithubUsersCache(private val db: Database) : IGithubUsersCache {

    override fun getUsers() = Single.fromCallable {
        db.userDao.getAll().map { roomUser ->
            GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
        }
    }

    override fun putUsers(users: List<GithubUser>) = Completable.fromAction {
        val roomUsers = users.map { user ->
            RoomGithubUser(
                user.id,
                user.login,
                user.avatarUrl,
                user.reposUrl
            )
        }
        db.userDao.insert(roomUsers)
    }.subscribeOn(Schedulers.io())
}