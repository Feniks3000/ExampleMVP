package ru.geekbrains.github_client.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepository(
    @Expose val id: String,
    @Expose val name: String,
    @Expose val url: String,
    @Expose val forksCount: String,
    @Expose val language: String?
) : Parcelable