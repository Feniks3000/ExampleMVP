package ru.geekbrains.github_client.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.repository.IGithubUsersRepo
import ru.geekbrains.github_client.mvp.navigation.IScreens
import ru.geekbrains.github_client.mvp.presenter.list.IUsersListPresenter
import ru.geekbrains.github_client.mvp.view.UsersView
import ru.geekbrains.github_client.mvp.view.list.IUserItemView

class UsersPresenter(
    private val usersRepo: IGithubUsersRepo,
    private val router: Router,
    private val screens: IScreens,
    private val mainThread: Scheduler
) :
    MvpPresenter<UsersView>() {

    class UsersListPresenter : IUsersListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
            view.loadAvatar(user.avatarUrl)
        }

        override fun getCount() = users.size
    }

    val compositeDisposable = CompositeDisposable()

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { view ->
            val user = usersListPresenter.users[view.pos]
            router.navigateTo(screens.user(user))
        }
    }

    fun loadData() {
        usersListPresenter.users.clear()
        val disposable = usersRepo.getUsers()
            .observeOn(mainThread)
            .subscribe({ users ->
                usersListPresenter.users.addAll(users)
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