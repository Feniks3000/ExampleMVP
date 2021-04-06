package ru.geekbrains.github_client.ui.activity

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.geekbrains.github_client.R
import ru.geekbrains.github_client.databinding.ActivityMainBinding
import ru.geekbrains.github_client.mvp.presenter.MainPresenter
import ru.geekbrains.github_client.mvp.view.MainView
import ru.geekbrains.github_client.ui.App
import ru.geekbrains.github_client.ui.BackClickListener
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.container)
    private val vb by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val presenter by moxyPresenter {
        MainPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        App.instance.appComponent.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment is BackClickListener && fragment.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }
}