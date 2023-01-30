package com.example.comparison.base

interface BaseView {

    fun showError(error: String)    // Error 를 출력하는 부분이 공통적으로 쓰인다는 가정하에 함수를 생성
    fun showToastMessage(msg : String)   // message 를 출력하는 부분이 공통적으로 쓰인다는 가정하에 함수를 생성
}