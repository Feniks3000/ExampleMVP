package ru.geekbrains.github_client.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Versioned(val version: Int)