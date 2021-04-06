package ru.geekbrains.github_client.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.github_client.mvp.model.api.IDataSource
import ru.geekbrains.github_client.mvp.model.network.INetworkStatus
import ru.geekbrains.github_client.ui.App
import ru.geekbrains.github_client.ui.network.AndroidNetworkStatus
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    fun baseUrl(): String = "https://api.github.com"

    @Singleton
    @Provides
    fun api(baseUrl: String, gson: Gson): IDataSource = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(IDataSource::class.java)

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Singleton
    @Provides
    fun networkStatus(app: App): INetworkStatus = AndroidNetworkStatus(app)
}