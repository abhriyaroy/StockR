package studio.zebro.research.ui.researchdetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import studio.zebro.core.BaseFragment
import studio.zebro.core.util.*
import studio.zebro.core.util.CoreUtility.DD_MMM_YYYY_DATE_FORMAT
import studio.zebro.core.util.CoreUtility.DD_MM_YY_DATE_FORMAT
import studio.zebro.core.util.CoreUtility.getStockPositiveNegativeState
import studio.zebro.datasource.util.ResourceState
import studio.zebro.research.R
import studio.zebro.research.databinding.FragmentResearchDetailBinding
import studio.zebro.research.domain.model.HistoricStockDataModel
import studio.zebro.research.domain.model.StockResearchModel
import studio.zebro.research.ui.research.ResearchViewModel
import java.util.*


@AndroidEntryPoint
class ResearchDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentResearchDetailBinding
    private lateinit var researchViewModel: ResearchViewModel
    private lateinit var stockResearchModel: StockResearchModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        researchViewModel =
            ViewModelProvider(requireActivity())[ResearchViewModel::class.java]
        researchViewModel.clearStockHistoricalData()
        return FragmentResearchDetailBinding.inflate(inflater).let {
            binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            stockResearchModel =
                ResearchDetailFragmentArgs.fromBundle(it).researchItem
            binding.stockResearchModelItem = stockResearchModel
        }
        startPostponedEnterTransition()
        attachClickListeners()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        researchViewModel.getStockHistoricalData(stockResearchModel)
    }

    private fun attachClickListeners() {
        binding.backImageButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupObservers() {
        researchViewModel.stockHistoricalData.observe(viewLifecycleOwner, {
            when (it) {
                is ResourceState.Success -> {
                    binding.layoutEmptyChartView.rootViewGroup.gone()
                    binding.chartLoadingLayout.root.gone()
                    configureLineChart(it.data)
                }
                is ResourceState.Loading -> {
                    binding.layoutEmptyChartView.rootViewGroup.gone()
                    binding.lineChart.invisible()
                    binding.chartLoadingLayout.root.visible()
                }
                is ResourceState.Error -> {
                    binding.chartLoadingLayout.root.gone()
                    binding.layoutEmptyChartView.rootViewGroup.visible()
                    binding.layoutEmptyChartView.emptyViewLottie.playAnimation()
                }
            }
        })
    }


    private fun configureLineChart(historicStockDataModel: HistoricStockDataModel) {
        binding.lineChart.visible()
        val desc = Description()
        desc.text = "Closing price history"
        desc.textSize = 12f
        desc.textColor = ContextCompat.getColor(requireContext(), R.color.white)
        binding.lineChart.description = desc

        val values: ArrayList<Entry> = ArrayList()

        historicStockDataModel.dataItemsList.forEachIndexed { index, value ->
            values.add(Entry(index.toFloat(), value.closePrice))
        }

        val lineDataSet = LineDataSet(values, "")
        lineDataSet.setDrawFilled(true)
        val fillGradient =
            when (getStockPositiveNegativeState(stockResearchModel.action)) {
                StockPositiveNegativeState.POSITVE -> {
                    lineDataSet.color = ContextCompat.getColor(requireContext(), R.color.positive)
                    lineDataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.positive))
                    ContextCompat.getDrawable(requireContext(), R.drawable.gradient_positive)
                }
                StockPositiveNegativeState.NEGATIVE -> {
                    lineDataSet.color = ContextCompat.getColor(requireContext(), R.color.negative)
                    lineDataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.negative))
                    ContextCompat.getDrawable(requireContext(), R.drawable.gradient_negative)
                }
            }
        lineDataSet.fillDrawable = fillGradient
        val lineData = LineData(lineDataSet)
        binding.lineChart.data = lineData
        binding.lineChart.xAxis.valueFormatter = XAxisValueFormatter(historicStockDataModel)
        binding.lineChart.xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.white)
        binding.lineChart.axisLeft.textColor =
            ContextCompat.getColor(requireContext(), R.color.white)
        binding.lineChart.axisRight.textColor =
            ContextCompat.getColor(requireContext(), R.color.white)
        lineDataSet.valueTextColor = Color.WHITE
        lineDataSet.valueTextSize = 18f
        binding.lineChart.setTouchEnabled(true)
        binding.lineChart.setPinchZoom(true)
        binding.lineChart.isDoubleTapToZoomEnabled = true
        binding.lineChart.animateX(1750)

    }

    private class XAxisValueFormatter(private val historicStockDataModel: HistoricStockDataModel) :
        ValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return CoreUtility.formatDate(
                historicStockDataModel.dataItemsList[value.toInt()].date,
                DD_MMM_YYYY_DATE_FORMAT,
                DD_MM_YY_DATE_FORMAT
            )
        }
    }

}