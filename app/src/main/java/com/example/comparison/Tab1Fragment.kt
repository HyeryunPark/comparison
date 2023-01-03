package com.example.comparison

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.comparison.databinding.FragmentTab1Binding


// Tab1Fragment.kt : 가격비교 탭(price comparison)
class Tab1Fragment : Fragment() {
    private var _binding: FragmentTab1Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTab1Binding.inflate(inflater, container, false)
        return binding.root
    }
}