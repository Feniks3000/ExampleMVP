package ru.geekbrains.github_client.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.github_client.databinding.FragmentUsersBinding
import ru.geekbrains.github_client.mvp.model.api.ApiHolder
import ru.geekbrains.github_client.mvp.model.entity.room.db.Database
import ru.geekbrains.github_client.mvp.model.repository.RetrofitGithubUsersRepo
import ru.geekbrains.github_client.mvp.presenter.UsersPresenter
import ru.geekbrains.github_client.mvp.view.UsersView
import ru.geekbrains.github_client.ui.App
import ru.geekbrains.github_client.ui.BackClickListener
import ru.geekbrains.github_client.ui.adapter.UsersRVAdapter
import ru.geekbrains.github_client.ui.image.GlideImageLoader
import ru.geekbrains.github_client.ui.navigation.AndroidScreens
import ru.geekbrains.github_client.ui.network.AndroidNetworkStatus

class UsersFragment : MvpAppCompatFragment(), UsersView, BackClickListener {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private val presenter by moxyPresenter {
        UsersPresenter(
            RetrofitGithubUsersRepo(
                ApiHolder.api,
                AndroidNetworkStatus(App.instance),
                Database.getInstance()
            ), App.instance.router, AndroidScreens(), AndroidSchedulers.mainThread()
        )
    }

    private var vb: FragmentUsersBinding? = null
    private var adapter: UsersRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUsersBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun init() {
        vb?.rvUsers?.layoutManager = LinearLayoutManager(requireContext())
        adapter = UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
        vb?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backClick()

}