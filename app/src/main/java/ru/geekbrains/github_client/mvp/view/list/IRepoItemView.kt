package ru.geekbrains.github_client.mvp.view.list

interface IRepoItemView : IItemView {
    fun setLanguage(language: String)
    fun setName(name: String)
}