package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model

object CountersModel {
    val counters = mutableListOf(0, 0, 0)

    fun getCurrent(index: Int) = counters[index]

    fun getNext(index: Int): Int {
        counters[index]++
        return getCurrent(index)
    }
}