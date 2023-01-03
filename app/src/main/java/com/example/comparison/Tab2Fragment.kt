package com.example.comparison

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.comparison.databinding.FragmentTab2Binding

// Tab2Fragment.kt : 가격변동 탭(price fluctuations)
class Tab2Fragment : Fragment() {
    private var _binding: FragmentTab2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentTab2Binding.inflate(inflater, container, false)
        return binding.root
    }
}