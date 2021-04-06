package ru.geekbrains.github_client.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.geekbrains.github_client.databinding.FragmentRepositoryBinding
import ru.geekbrains.github_client.mvp.model.entity.GithubRepository
import ru.geekbrains.github_client.mvp.presenter.RepositoryPresenter
import ru.geekbrains.github_client.mvp.view.RepositoryView
import ru.geekbrains.github_client.ui.App
import ru.geekbrains.github_client.ui.BackClickListener


class RepositoryFragment : MvpAppCompatFragment(), RepositoryView, BackClickListener {

    companion object {
        private const val REPOSITORY_ARG = "repository"

        fun newInstance(repository: GithubRepository) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY_ARG, repository)
            }
        }
    }

    private var vb: FragmentRepositoryBinding? = null

    val presenter by moxyPresenter {
        RepositoryPresenter(arguments?.getParcelable<GithubRepository>(REPOSITORY_ARG) as GithubRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentRepositoryBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun init() {}

    override fun setId(text: String) {
        vb?.tvId?.text = text
    }

    override fun setTitle(text: String) {
        vb?.tvTitle?.text = text
    }

    override fun setForksCount(text: String) {
        vb?.tvForksCount?.text = text
    }

    override fun backPressed() = presenter.backPressed()
}