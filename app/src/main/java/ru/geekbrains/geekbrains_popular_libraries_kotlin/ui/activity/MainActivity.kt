package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.ActivityMainBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.MainPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.MainView

class MainActivity : AppCompatActivity(), MainView {

    private val vb: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        presenter = MainPresenter(this)

        vb.btnCounter1.setOnClickListener { presenter?.counter1Click() }
        vb.btnCounter2.setOnClickListener { presenter?.counter2Click() }
        vb.btnCounter3.setOnClickListener { presenter?.counter3Click() }
    }

    override fun setButton1Text(text: String) {
        vb.btnCounter1.text = text
    }

    override fun setButton2Text(text: String) {
        vb.btnCounter2.text = text
    }

    override fun setButton3Text(text: String) {
        vb.btnCounter3.text = text
    }
}