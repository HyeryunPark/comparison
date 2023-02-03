package com.example.comparison.comparison

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comparison.databinding.FragmentTab1Binding


// Tab1Fragment.kt : 가격비교 탭(price comparison)
class Tab1Fragment : Fragment() {
    private var _binding: FragmentTab1Binding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: Tab1Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTab1Binding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    // recyclerview
    private fun initRecyclerView() {
        adapter = Tab1Adapter()

        binding.rvTab1.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvTab1.adapter = this@Tab1Fragment.adapter

    }

}