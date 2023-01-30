package com.example.comparison.main

class MainPresenter : MainContract.Presenter {

    private var mainView: MainContract.View? = null

    override fun setView(view: MainContract.View) {
        mainView = view
    }

    override fun dropView() {
        mainView = null
    }


}