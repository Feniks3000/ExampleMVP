package ru.geekbrains.github_client.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.github_client.mvp.model.GithubUsersRepo
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.navigation.IScreens
import ru.geekbrains.github_client.mvp.presenter.list.IUsersListPresenter
import ru.geekbrains.github_client.mvp.view.UsersView
import ru.geekbrains.github_client.mvp.view.list.IUserItemView

class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router, val screens: IScreens) :
    MvpPresenter<UsersView>() {

    class UsersListPresenter : IUsersListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((IUserItemView) -> Unit)? = null

        override fun bindView(view: IUserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

        override fun getCount() = users.size
    }

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
        usersRepo.getUsers().subscribe { user -> usersListPresenter.users.add(user) }
        viewState.updateList()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}