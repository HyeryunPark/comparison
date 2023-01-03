package com.example.comparison

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.comparison.databinding.FragmentTab3Binding

// Tab3Fragment.kt : 상품후기 탭(product review)
class Tab3Fragment : Fragment() {
    private var _binding: FragmentTab3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTab3Binding.inflate(inflater, container, false)
        return binding.root
    }
}