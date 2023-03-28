package com.example.comparison.comparison.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.comparison.model.PricesData

// Tab1Fragment, Tab2Fragment 가 동일한 데이터를 공유하게 하기위해 사용
class PricesViewModel : ViewModel() {

    val itemList = ListLiveData<PricesData>()

    fun addItem(item: PricesData){
        itemList.add(item)
        Log.e("pricesViewModel-addItem", itemList.toString())
    }


/*
    private val _pricesDataList = MutableLiveData<PricesData>()

    val pricesDataList : MutableLiveData<PricesData>
        get() = _pricesDataList

    fun setData(dataList: PricesData){
        pricesDataList.value = dataList
        Log.e("PriceViewModel-setData",pricesDataList.value.toString())
    }
*/



}