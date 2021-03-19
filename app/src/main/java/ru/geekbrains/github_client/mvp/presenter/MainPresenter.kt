package ru.geekbrains.github_client.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.github_client.mvp.navigation.IScreens
import ru.geekbrains.github_client.mvp.view.MainView
import ru.geekbrains.github_client.rx_learn.Operators

class MainPresenter(val router: Router, val screens: IScreens) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())

        //Creation().exec()
        Operators().exec()
    }

    fun backClicked() {
        router.exit()
    }
}