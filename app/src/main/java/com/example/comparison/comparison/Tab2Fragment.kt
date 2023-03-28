package com.example.comparison.comparison

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.comparison.comparison.model.PricesViewModel
import com.example.comparison.databinding.FragmentTab2Binding
import com.example.comparison.model.GetMainData
import com.example.comparison.model.PricesData
import com.example.comparison.network.RetrofitClient
import com.example.comparison.network.RetrofitInterface
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// Tab2Fragment.kt : 가격변동 탭(price fluctuations)

class Tab2Fragment : Fragment() {
    private val TAG = "Tab2Fragment"

    private var _binding: FragmentTab2Binding? = null
    private val binding get() = _binding!!

    private val viewModel: PricesViewModel by activityViewModels()

    private lateinit var lineChart: LineChart

    // 데이터 생성 - 추후 삭제
   /* private val dataList: MutableList<PricesData> = mutableListOf(
        PricesData(20231111, 11111),
        PricesData(20232222, 22222),
        PricesData(20233333, 33333)
    )*/
    private var dataList = mutableListOf<PricesData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val p_code = requireActivity().intent.getIntExtra("p_code", 0)
        Log.e("$TAG - pCode", p_code.toString())

//        loadData(p_code = p_code)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTab2Binding.inflate(inflater, container, false)

//        val lineChart: LineChart = _binding!!.lineChart
        lineChart = _binding!!.lineChart

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel
//        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory()).get(PricesViewModel::class.java)

        viewModel.itemList.observe(viewLifecycleOwner, Observer {
            Log.e("viewModel-tab2fragment-it", it.toString())

            dataList.addAll(it)
            Log.e("viewModel-tab2fragment-dataList", dataList.toString())

            initLineChart(lineChart)
        })

    }


    // line chart 초기셋팅
    private fun initLineChart(lineChart: LineChart) {
        Log.e(TAG, "initLineChart")

        // 차트 전체 셋팅

        // lineChart 세팅
        /* LineEntry - (x,y) 쌍으로 Line Chart에 표시될 데이터를 저장하여 이를 리스트에 추가한다.
        LineDataSet - 단순 데이터인 LineData를 라인 모양으로 표시하기 위해 필요하며, 모양을 설정한다.
        LineData - 보여질 데이터를 구성한다.*/

        lineChart.apply {
            axisRight.isEnabled = false // y축의 오른쪽 데이터 사용여부
//            axisLeft.axisMaximum = 50f  // y축의 왼쪽 데이터 최대값은 50
            axisLeft.isEnabled = false  // y축의 왼쪽 데이터 사용여부
/*          // 범례 세팅
            legend.apply {
                textSize = 14f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP  // 수직조정 -> 위로
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER   // 수평 조정 -> 가운데
                orientation = Legend.LegendOrientation.HORIZONTAL   // 범례와 차트 정렬 -> 수평
                setDrawInside(false)    // 차트 내부에 위치하게 할것인가
            }
*/
            legend.isEnabled = false    // legend 사용여부
            description.isEnabled = false // chart 밑에 description 표시 유무 (주석)
            isDragXEnabled = true   // x 축 드래그 여부
            isScaleYEnabled = false //y축 줌 사용여부
            isScaleXEnabled = false //x축 줌 사용여부
            setTouchEnabled(false)  // 터치 유무
        }

        // xAxis - x축 설정
        val xAxis = lineChart.xAxis
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM   // x축 데이터의 위치를 아래로
            textSize = 12f  // 텍스트 크기 (float 형으로 해야함)
            setDrawGridLines(false) // 배경 그리드 라인 세팅
            granularity = 1f    // x축 데이터 표시 간격
//            axisMinimum = 0f    // x축 데이터의 최소 표시값
//            axisMaximum = 4f    // x축 데이터의 최대 표시값
//            valueFormatter    // (MM.DD) 월.일 형태로 바꾸기
            valueFormatter = XAxisCustomFormatter(changeDateText(dataList))


        }

        // y축 설정
        val entries: MutableList<Entry> = mutableListOf()
        for (i in dataList.indices) {
            entries.add(Entry(i.toFloat(), dataList[i].low_price.toFloat()))
        }
        val lineDataSet = LineDataSet(entries, "entries")

        lineDataSet.apply {
            color = Color.rgb(140, 145, 132)
            setCircleColor(Color.rgb(140, 145, 132))
            valueTextSize = 10f
        }

        lineChart.apply {
            data = LineData(lineDataSet)
            notifyDataSetChanged()  // 데이터 갱신
            invalidate()    // view 갱신
        }
    }

    // 20230306 형태의 Int 를 0306 형태의 String 으로 변환
    private fun changeDateText(dataList: List<PricesData>): List<String> {
        val dataTextList = ArrayList<String>()
        for (i in dataList.indices) {
            val dateText = dataList[i].date.toString().substring(4 until 8)
//            Log.e("date", dataList[i].date.toString())
//            Log.e("dateText", dateText)
            dataTextList.add((dateText))
        }
        return dataTextList
    }

    // valueFormatter 에 적용시키기 위해서 ValueFormatter 추상 클래스를 상속해서 사용
    class XAxisCustomFormatter(private val xAxisData: List<String>) : ValueFormatter() {

        override fun getFormattedValue(value: Float): String {

            return xAxisData[(value).toInt()]
        }
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

                                dataList.add(addData)
                                Log.e("dataList", dataList.toString())
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