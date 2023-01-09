package com.example.comparison

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.comparison.databinding.FragmentTab2Binding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

// Tab2Fragment.kt : 가격변동 탭(price fluctuations)
class Tab2Fragment : Fragment() {
    private var _binding: FragmentTab2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentTab2Binding.inflate(inflater, container, false)

        val lineChart: LineChart = _binding!!.lineChart

        initLineChart(lineChart)
        setLineData(lineChart)

        return binding.root
    }


    // line chart 초기셋팅
    private fun initLineChart(lineChart: LineChart) {
        Log.e("initLineChart", "차트 초기세팅")
        lineChart.description.isEnabled = false // chart 밑에 description 표시 유무
        lineChart.setTouchEnabled(false)    // 터치 유무

        // xAxis (아래쪽 x축)
        val xAxis = lineChart.xAxis
        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM   // x축 데이터의 위치를 아래로
            textSize = 12f  // 텍스트 크기 (float 형으로 해야함)
            setDrawGridLines(false) // 배경 그리드 라인 세팅
            granularity = 1f    // x축 데이터 표시 간격
            axisMinimum = 0f    // x축 데이터의 최소 표시값
            axisMaximum = 4f    // x축 데이터의 최대 표시값
//            valueFormatter    // (MM.DD) 월.일 형태로 바꾸기
        }

        // yAxis (y축 왼쪽)
        val yAxis = lineChart.axisLeft
        yAxis.apply {
            axisMinimum = 0f
        }

        // lineChart 세팅
        /* LineEntry - (x,y) 쌍으로 Line Chart에 표시될 데이터를 저장하여 이를 리스트에 추가한다.
        LineDataSet - 단순 데이터인 LineData를 라인 모양으로 표시하기 위해 필요하며, 모양을 설정한다.
        LineData - 보여질 데이터를 구성한다.*/

        lineChart.apply {
            axisRight.isEnabled = false // y축의 오른쪽 데이터 비활성화
            axisLeft.axisMaximum = 50f  // y축의 왼쪽 데이터 최대값은 50
            // 범례 세팅
            legend.apply {
                textSize = 14f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP  // 수직조정 -> 위로
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER   // 수평 조정 -> 가운데
                orientation = Legend.LegendOrientation.HORIZONTAL   // 범례와 차트 정렬 -> 수평
                setDrawInside(false)    // 차트 내부에 위치하게 할것인가
            }
        }
    }

    // 데이터 생성
    private fun setLineData(lineChart: LineChart) {
        Log.e("setLineData", "데이터 생성")
        val dataList = ArrayList<Entry>()

        // 임의 데이터
        dataList.add(Entry(1f, 40f))
        dataList.add(Entry(2f, 30f))
        dataList.add(Entry(3f, 10f))
        Log.e("lineDataList", dataList.toString())


        val lineDataSet = LineDataSet(dataList, "lineChart")
        lineDataSet.setColors(Color.rgb(140, 145, 132))
        lineDataSet.setCircleColor(Color.rgb(140, 145, 132))


        val data = LineData(lineDataSet)
        lineChart.data = data
        lineChart.invalidate()

    }
}