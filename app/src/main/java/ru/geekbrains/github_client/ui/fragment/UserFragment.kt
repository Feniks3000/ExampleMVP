package ru.geekbrains.github_client.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.github_client.databinding.FragmentUserBinding
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.presenter.UserPresenter
import ru.geekbrains.github_client.mvp.view.UserView
import ru.geekbrains.github_client.ui.App
import ru.geekbrains.github_client.ui.BackClickListener

class UserFragment : MvpAppCompatFragment(), UserView, BackClickListener {

    companion object {
        private const val USER = "user"
        fun newInstance(user: GithubUser) = UserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER, user)
            }
        }
    }

    private val presenter by moxyPresenter {
        UserPresenter(App.instance.router, arguments?.get(USER) as GithubUser)
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

    override fun setLogin(login: String) {
        vb?.tvUser?.text = login
    }


    override fun backPressed() = presenter.backClick()

}