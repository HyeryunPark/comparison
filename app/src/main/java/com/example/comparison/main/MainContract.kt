package com.example.comparison.main

import android.content.Context
import android.text.Editable
import com.example.comparison.base.BasePresenter
import com.example.comparison.base.BaseView
import com.example.comparison.database.MainDao
import com.example.comparison.database.MainInfo

//  contract : view 와 presenter 가 구현해야할 인터페이스를 정의
interface MainContract {

    interface View : BaseView {

        fun setFloatingButton()
        fun sendDataNextView(price: String, img: String)
    }

    interface Presenter : BasePresenter<View> {

        // 사용자가 입력한 url 을 서버로 보내 상품 정보를 받아오는 함수
        fun loadData(p_code: Int)

        fun setDatabase(context: Context)

        // 서버에서 받아온 상품정보 db에 저장하는 함수
       fun addData(mainInfo: MainInfo)

       // db에 저장된 모든 데이터를 불러오는 함수
       fun loadAllData(dataList: List<MainInfo>)
    }


}

