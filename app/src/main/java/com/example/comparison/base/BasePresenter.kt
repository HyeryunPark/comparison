package com.example.comparison.base

interface BasePresenter<T> {


    fun setView(view: T)    // view 가 생성 혹은 bind 될 때를 presenter 에 전달
    fun dropView()          // view 가 제거되거나 unbind 될 때를 presenter 에 전달
}