package ru.geekbrains.github_client.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.view.RepositoryView

class RepositoryPresenter(private val router: Router, private val githubRepository: GithubRepository) :
    MvpPresenter<RepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setId(githubRepository.id)
        viewState.setTitle(githubRepository.name)
        viewState.setForksCount(githubRepository.forksCount)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
