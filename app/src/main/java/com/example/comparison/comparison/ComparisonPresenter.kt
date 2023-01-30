package com.example.comparison.comparison

class ComparisonPresenter : ComparisonContract.Presenter {

    private var comparisonView: ComparisonContract.View? = null

    override fun setView(view: ComparisonContract.View) {
        comparisonView = view
    }

    override fun dropView() {
        comparisonView = null
    }
}