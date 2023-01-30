package com.example.comparison.comparison

import com.example.comparison.base.BasePresenter
import com.example.comparison.base.BaseView

interface ComparisonContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {

    }
}