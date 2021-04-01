package ru.geekbrains.github_client.mvp.model.image

interface IImageLoader<T> {
    fun load(url: String, container: T)
}