package ru.geekbrains.github_client.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.github_client.mvp.navigation.IScreens
import ru.geekbrains.github_client.mvp.view.MainView

class MainPresenter(private val router: Router, private val screens: IScreens) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }
}