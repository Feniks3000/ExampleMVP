package ru.geekbrains.github_client.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.navigation.IScreens
import ru.geekbrains.github_client.mvp.model.repository.IGithubRepositoriesRepo
import ru.geekbrains.github_client.mvp.presenter.list.IRepositoryListPresenter
import ru.geekbrains.github_client.mvp.view.UserView
import ru.geekbrains.github_client.mvp.view.list.IRepositoryItemView
import javax.inject.Inject

class UserPresenter(
    private val mainThread: Scheduler,
    private val user: GithubUser
) :
    MvpPresenter<UserView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: IScreens

    @Inject
    lateinit var repositoriesRepo: IGithubRepositoriesRepo

    class RepositoriesListPresenter : IRepositoryListPresenter {
        val repositories = mutableListOf<GithubRepository>()
        override var itemClickListener: ((IRepositoryItemView) -> Unit)? = null

        override fun bindView(view: IRepositoryItemView) {
            val repo = repositories[view.pos]
            view.setLanguage(repo.language ?: "-")
            view.setName(repo.name)
        }

        override fun getCount() = repositories.size
    }

    val compositeDisposable = CompositeDisposable()

    val repositoriesListPresenter = RepositoriesListPresenter()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setLogin(user.login)
        loadData()

        repositoriesListPresenter.itemClickListener = { view ->
            //viewState.showMessage(repositoriesListPresenter.repositories[view.pos].id)
            val repository = repositoriesListPresenter.repositories[view.pos]
            router.navigateTo(screens.repository(repository))
        }

    }

    fun loadData() {
        repositoriesListPresenter.repositories.clear()
        val disposable = repositoriesRepo.getRepositories(user)
            .observeOn(mainThread)
            .subscribe({ repos ->
                repositoriesListPresenter.repositories.addAll(repos)
                viewState.updateList()
            }, { exception ->
                println("Error: ${exception.message}")
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