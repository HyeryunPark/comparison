package com.example.comparison.comparison

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comparison.comparison.model.PricesViewModel
import com.example.comparison.databinding.FragmentTab1Binding
import com.example.comparison.model.GetMainData
import com.example.comparison.model.PricesData
import com.example.comparison.network.RetrofitClient
import com.example.comparison.network.RetrofitInterface
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


// Tab1Fragment.kt : 가격비교 탭(price comparison)
class Tab1Fragment : Fragment() {
    private var _binding: FragmentTab1Binding? = null
    private val binding get() = _binding!!

    private val viewModel: PricesViewModel by activityViewModels()

    private lateinit var adapter: Tab1Adapter
    private var list = mutableListOf<PricesData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val p_code = requireActivity().intent.getIntExtra("p_code", 0)
        Log.e("p_codeeeeeeeeeeee", p_code.toString())
        loadData(p_code = p_code)


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel
//        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(PricesViewModel::class.java)
        viewModel.itemList.observe(viewLifecycleOwner, Observer {

        })
    }


    // recyclerview
    private fun initRecyclerView() {
        adapter = Tab1Adapter(list = list)

        binding.rvTab1.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
        binding.rvTab1.adapter = this@Tab1Fragment.adapter
    }

    // 상품의 p_code 를 서버로 보내 상품 정보(날짜, 최저가)를 받아오는 함수
    private fun loadData(p_code: Int) {
        val retrofit: Retrofit = RetrofitClient.getInstance()
        val retrofitInterface: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

        retrofitInterface.getDataInfoPCode(p_code = p_code)
            .enqueue(object : Callback<GetMainData> {
                override fun onResponse(call: Call<GetMainData>, response: Response<GetMainData>) {
                    if (response.isSuccessful) {
                        // onResponse 통신 성공시 Callback ( 메인스레드에서 작업하는 부분 (UI 작업 가능))
                        Log.e("onResponse 성공: ", Gson().toJson(response.body()))

                        val body = response.body()
                        body.let {
                            for (i in body!!.prices.indices) {
                                val date = body.prices[i].date
                                val low_price = body.prices[i].low_price

                                val addData = PricesData(
                                    date = date,
                                    low_price = low_price
                                )

                                adapter.addItem(addData)
                                Log.e("addData", addData.toString())


//                                viewModel.setData(addData)
                                viewModel.addItem(addData)
                            }
                        }

                    } else {
                        // onResponse 가 무조건 성공 응답이 아니기에 확인 필요 (응답 코드 3xx, 4xx 호출)
                        Log.e("onResponse 실패: ", "")
                    }
                }

                override fun onFailure(call: Call<GetMainData>, t: Throwable) {
                    // onFailure 통신 실패시 Callback ( 인터넷 끊김, 예외 발생 등 시스템적인 이유 )
                    Log.e("onFailure: ", t.toString())
                }

            })


    }

}