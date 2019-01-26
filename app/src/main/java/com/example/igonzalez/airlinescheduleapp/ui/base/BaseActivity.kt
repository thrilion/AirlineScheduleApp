package com.example.igonzalez.airlinescheduleapp.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<P : BasePresenter<BaseView>> : AppCompatActivity(), BaseView {
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = setPresenter()
    }

    override fun getContext(): Context {
        return this
    }

    protected abstract fun setPresenter(): P

    abstract fun showLoading()

    abstract fun hideLoading()

    abstract fun showError(error: String)
}