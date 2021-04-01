package ru.geekbrains.github_client.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.github_client.databinding.FragmentUserBinding
import ru.geekbrains.github_client.mvp.model.api.ApiHolder
import ru.geekbrains.github_client.mvp.model.entity.GithubUser
import ru.geekbrains.github_client.mvp.model.repository.RetrofitGithubRepositoriesRepo
import ru.geekbrains.github_client.mvp.presenter.UserPresenter
import ru.geekbrains.github_client.mvp.view.UserView
import ru.geekbrains.github_client.ui.App
import ru.geekbrains.github_client.ui.BackClickListener
import ru.geekbrains.github_client.ui.adapter.ReposRVAdapter

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
        UserPresenter(
            RetrofitGithubRepositoriesRepo(ApiHolder.api),
            App.instance.router,
            AndroidSchedulers.mainThread(),
            arguments?.get(USER) as GithubUser
        )
    }

    private var vb: FragmentUserBinding? = null
    private var adapter: ReposRVAdapter? = null

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

    override fun init() {
        vb?.rvRepos?.layoutManager = LinearLayoutManager(requireContext())
        adapter = ReposRVAdapter(presenter.reposListPresenter)
        vb?.rvRepos?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun backPressed() = presenter.backClick()
}