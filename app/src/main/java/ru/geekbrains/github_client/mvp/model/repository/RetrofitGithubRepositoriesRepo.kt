package ru.geekbrains.github_client.mvp.model.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.github_client.mvp.model.api.IDataSource
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.github_client.mvp.model.entity.room.db.Database
import ru.geekbrains.github_client.mvp.model.network.INetworkStatus

class RetrofitGithubRepositoriesRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val db: Database
) : IGithubRepositoriesRepo {

    override fun getRepositories(user: GithubUser) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if(isOnline){
            user.reposUrl?.let { url ->
                api.getRepositories(url)
                    .flatMap { repositories ->
                        Single.fromCallable {
                            val roomUser = db.userDao.findByLogin(user.login) ?: throw RuntimeException("No user in DB")
                            val roomRepos = repositories.map { repo ->
                                RoomGithubRepository(repo.id, repo.name, repo.url, repo.forksCount, roomUser.id, repo.language)
                            }
                            db.repositoryDao.insert(roomRepos)
                            repositories
                        }
                    }
            } ?: Single.error(RuntimeException("User has no repos url"))
        } else {
            Single.fromCallable {
                val roomUser = db.userDao.findByLogin(user.login) ?: throw RuntimeException("No user in DB")
                db.repositoryDao.findForUser(roomUser.id).map { roomRepo ->
                    GithubRepository(roomRepo.id, roomRepo.name, roomRepo.url, roomRepo.forksCount, roomRepo.language)
                }
            }
        }
    }.subscribeOn(Schedulers.io())

}