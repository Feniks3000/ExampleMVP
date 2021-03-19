package ru.geekbrains.github_client.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface UserView : MvpView {
    fun setLogin(login: String)
}