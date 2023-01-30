package com.example.comparison.main

import com.example.comparison.base.BasePresenter
import com.example.comparison.base.BaseView

//  contract : view 와 presenter 가 구현해야할 인터페이스를 정의
interface MainContract {

    interface View : BaseView {


    }

    interface Presenter : BasePresenter<View> {


    }


}

