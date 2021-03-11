package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.CountersModel
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.MainView

class MainPresenter(val mainView: MainView) {
    val model = CountersModel

    init {
        initButtons()
    }

    fun counter1Click() {
        mainView.setButton1Text(model.getNext(0).toString())
    }

    fun counter2Click() {
        mainView.setButton2Text(model.getNext(1).toString())
    }

    fun counter3Click() {
        mainView.setButton3Text(model.getNext(2).toString())
    }

    fun initButtons() {
        mainView.setButton1Text(model.getCurrent(0).toString())
        mainView.setButton2Text(model.getCurrent(1).toString())
        mainView.setButton3Text(model.getCurrent(2).toString())
    }
}