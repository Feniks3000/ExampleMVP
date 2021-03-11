package ru.geekbrains.github_client.ui.activity

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.geekbrains.github_client.R
import ru.geekbrains.github_client.databinding.ActivityMainBinding
import ru.geekbrains.github_client.mvp.presenter.MainPresenter
import ru.geekbrains.github_client.mvp.view.MainView
import ru.geekbrains.github_client.ui.App
import ru.geekbrains.github_client.ui.BackClickListener
import ru.geekbrains.github_client.ui.navigation.AndroidScreens

class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigator = AppNavigator(this, R.id.container)
    private val vb by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val presenter by moxyPresenter { MainPresenter(App.instance.router, AndroidScreens()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
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