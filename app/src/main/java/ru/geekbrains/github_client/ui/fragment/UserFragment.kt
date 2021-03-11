package ru.geekbrains.github_client.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.github_client.databinding.FragmentUserBinding
import ru.geekbrains.github_client.mvp.model.GithubUsersRepo
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.presenter.UserPresenter
import ru.geekbrains.github_client.mvp.view.UserView
import ru.geekbrains.github_client.ui.App
import ru.geekbrains.github_client.ui.BackClickListener

class UserFragment(val user: GithubUser) : MvpAppCompatFragment(), UserView, BackClickListener {

    companion object {
        fun newInstance(user: GithubUser) = UserFragment(user)
    }

    private val presenter by moxyPresenter {
        UserPresenter(GithubUsersRepo(), App.instance.router)
    }

    private var vb: FragmentUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.tvUser?.text = user.login
    }


    override fun backPressed() = presenter.backClick()

}