package ru.geekbrains.github_client.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.geekbrains.github_client.mvp.model.entity.GithubRepo
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.repository.IGithubRepositoriesRepo
import ru.geekbrains.github_client.mvp.presenter.list.IReposListPresenter
import ru.geekbrains.github_client.mvp.view.UserView
import ru.geekbrains.github_client.mvp.view.list.IRepoItemView

class UserPresenter(
    private val repositoriesRepo: IGithubRepositoriesRepo,
    private val router: Router,
    private val mainThread: Scheduler,
    private val user: GithubUser
) :
    MvpPresenter<UserView>() {

    class ReposListPresenter : IReposListPresenter {
        val repos = mutableListOf<GithubRepo>()
        override var itemClickListener: ((IRepoItemView) -> Unit)? = null

        override fun bindView(view: IRepoItemView) {
            val repo = repos[view.pos]
            view.setLanguage(repo.language ?: "-")
            view.setName(repo.name)
        }

        override fun getCount() = repos.size
    }

    val compositeDisposable = CompositeDisposable()

    val reposListPresenter = ReposListPresenter()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setLogin(user.login)
        loadData(user.reposUrl)

        reposListPresenter.itemClickListener = { view ->
            viewState.showMessage(reposListPresenter.repos[view.pos].id.toString())
        }

    }

    fun loadData(url: String) {
        reposListPresenter.repos.clear()
        val disposable = repositoriesRepo.getUserRepos(url)
            .observeOn(mainThread)
            .subscribe({ repos ->
                reposListPresenter.repos.addAll(repos)
                viewState.updateList()
            }, { exception ->
                exception.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}