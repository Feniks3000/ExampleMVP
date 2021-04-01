package ru.geekbrains.github_client.mvp.view.list

interface IRepositoryItemView : IItemView {
    fun setLanguage(language: String)
    fun setName(name: String)
}