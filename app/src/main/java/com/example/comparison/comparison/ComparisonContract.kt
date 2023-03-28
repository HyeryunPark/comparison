package com.example.comparison.comparison

import com.example.comparison.base.BasePresenter
import com.example.comparison.base.BaseView

interface ComparisonContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {


        // 사용자가 선택한 상품의 p_code 를 서버로 보내 상품 정보를 받아오는 함수
        fun loadData(p_code: Int)

    }
}