package ru.geekbrains.github_client.mvp.model.cache.room

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.github_client.mvp.model.cache.IGithubRepositoriesCache
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.github_client.mvp.model.entity.room.db.Database

class RoomGithubRepositoriesCache(val db: Database) : IGithubRepositoriesCache {

    override fun getUserRepositories(user: GithubUser) = Single.fromCallable {
        val roomUser = db.userDao.findByLogin(user.login) ?: throw RuntimeException("User is not exist in cache")
        return@fromCallable db.repositoryDao.findForUser(roomUser.id).map { repository ->
                GithubRepository(
                    repository.id,
                    repository.name,
                    repository.url,
                    repository.forksCount,
                    repository.language
                )
            }
    }.subscribeOn(Schedulers.io())

    override fun putUserRepositories(user: GithubUser, repositories: List<GithubRepository>) =
        Completable.fromAction {
            val roomUser = db.userDao.findByLogin(user.login) ?: throw RuntimeException("User is not exist in cache")
            val roomRepos = repositories.map { repository ->
                RoomGithubRepository(
                    repository.id,
                    repository.name,
                    repository.url,
                    repository.forksCount,
                    roomUser.id,
                    repository.language
                )
            }
            db.repositoryDao.insert(roomRepos)
        }.subscribeOn(Schedulers.io())
}