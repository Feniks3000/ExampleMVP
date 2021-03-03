package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.geekbrains_popular_libraries_kotlin.databinding.ActivityMainBinding
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.MainPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.MainView

class MainActivity : AppCompatActivity(), MainView {

    private val vb: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var buttons: MutableList<Button>

    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        buttons = mutableListOf(vb.btnCounter1, vb.btnCounter2, vb.btnCounter3)

        buttons.forEachIndexed { i, button ->
            button.setOnClickListener { presenter.counterClick(i) }
            presenter.initButtonText(i)
        }
    }

    override fun setButtonText(index: Int, text: String) {
        buttons[index].text = text
    }
}