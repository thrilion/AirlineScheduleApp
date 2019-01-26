package com.example.igonzalez.airlinescheduleapp.ui.base

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    init {
        inject()
    }

    open fun onViewCreated(){}

    open fun onViewDestroyed(){}

    private fun inject() {
        //TODO: Implement this method
    }
}