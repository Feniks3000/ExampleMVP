package ru.geekbrains.github_client.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.view.RepositoryView
import javax.inject.Inject

class RepositoryPresenter(private val githubRepository: GithubRepository) : MvpPresenter<RepositoryView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setId(githubRepository.id)
        viewState.setTitle(githubRepository.name)
        viewState.setForksCount(githubRepository.forksCount.toString())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
