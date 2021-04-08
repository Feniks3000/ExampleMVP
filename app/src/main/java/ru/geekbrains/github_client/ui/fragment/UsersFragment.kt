package ru.geekbrains.github_client.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.github_client.databinding.FragmentUsersBinding
import ru.geekbrains.github_client.mvp.presenter.UsersPresenter
import ru.geekbrains.github_client.mvp.view.UsersView
import ru.geekbrains.github_client.ui.App
import ru.geekbrains.github_client.ui.BackClickListener
import ru.geekbrains.github_client.ui.adapter.UsersRVAdapter
import javax.inject.Inject
import javax.inject.Named

class UsersFragment : MvpAppCompatFragment(), UsersView, BackClickListener {

    companion object {
        fun newInstance() = UsersFragment()
    }

    private val presenter by moxyPresenter {
        UsersPresenter().apply { App.instance.appComponent.inject(this) }
    }

    @field:Named("ui")
    @Inject
    lateinit var uiScheduler: Scheduler

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
        adapter = UsersRVAdapter(presenter.usersListPresenter).apply {
            App.instance.appComponent.inject(this)
        }
        vb?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backClick()

}