package com.example.comparison.comparison

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comparison.databinding.FragmentTab3Binding

// Tab3Fragment.kt : 상품후기 탭(product review)
class Tab3Fragment : Fragment() {
    private var _binding: FragmentTab3Binding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: Tab3Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTab3Binding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }


    private fun initRecyclerView(){
        adapter = Tab3Adapter()

        binding.rvTab3.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding.rvTab3.adapter = this@Tab3Fragment.adapter
    }

}
