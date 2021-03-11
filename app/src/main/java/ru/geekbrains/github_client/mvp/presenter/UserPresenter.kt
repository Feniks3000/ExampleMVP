package ru.geekbrains.github_client.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.github_client.mvp.model.GithubUsersRepo
import ru.geekbrains.github_client.mvp.view.UserView

class UserPresenter(val usersRepo: GithubUsersRepo, val router: Router) :
    MvpPresenter<UserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}