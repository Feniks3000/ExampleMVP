package ru.geekbrains.github_client.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.github_client.databinding.ItemRepoBinding
import ru.geekbrains.github_client.mvp.presenter.list.IRepositoryListPresenter
import ru.geekbrains.github_client.mvp.view.list.IRepositoryItemView

class ReposRVAdapter(val presenter: IRepositoryListPresenter) :
    RecyclerView.Adapter<ReposRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun getItemCount() = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    inner class ViewHolder(val vb: ItemRepoBinding) : RecyclerView.ViewHolder(vb.root),
        IRepositoryItemView {

        override var pos = -1

        override fun setLanguage(language: String) = with(vb) {
            tvLanguage.text = language
        }

        override fun setName(name: String) = with(vb) {
            tvName.text = name
        }
    }
}