package com.example.comparison

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//ViewPager2Adapter.kt
class ViewPager2Adapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        Log.e("ViewPager2Adapter","getItemCount ${fragments.size}")

        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.e("ViewPager2Adapter","createFragment $position")
        return fragments[position]
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
        //TODO: notifyItemInserted!!
    }

    fun removeFragement() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
        //TODO: notifyItemRemoved!!
    }

}