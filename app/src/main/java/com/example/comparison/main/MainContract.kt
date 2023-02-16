package com.example.comparison.main

import android.content.Context
import android.text.Editable
import com.example.comparison.base.BasePresenter
import com.example.comparison.base.BaseView

//  contract : view 와 presenter 가 구현해야할 인터페이스를 정의
interface MainContract {

    interface View : BaseView {

        fun setFloatingButton()
        fun sendDataNextView(price: String)

    }

    interface Presenter : BasePresenter<View> {

        fun loadData(p_code: String)

    }


}

