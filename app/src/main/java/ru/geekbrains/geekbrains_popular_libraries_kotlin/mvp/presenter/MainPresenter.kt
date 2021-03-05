package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.CountersModel
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.MainView

class MainPresenter(val mainView: MainView) {
    val model = CountersModel

    init {
        model.counters.forEachIndexed { i, _ ->
            initButtonText(i)
        }
    }

    fun counterClick(id: Int) {
        mainView.setButtonText(id, model.getNext(id).toString())
    }

    fun initButtonText(id: Int) {
        mainView.setButtonText(id, model.getCurrent(id).toString())
    }
}