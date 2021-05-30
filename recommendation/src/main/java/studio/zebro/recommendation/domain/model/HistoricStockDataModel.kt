package studio.zebro.recommendation.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoricStockDataModel(
    val dataSetAllTimeHigh: Float,
    val dataSetAllTimeLow: Float,
    val dataSetStartDate: String,
    val dataSetEndDate: String,
    val dataItemsList: List<HistoricStockDataDayWiseModel>
) : Parcelable

@Parcelize
data class HistoricStockDataDayWiseModel(
    val date: String,
    val stockName: String,
    val stockSeries: String,
    val openPrice: Float,
    val highPrice: Float,
    val lowPrice: Float,
    val ltpPrice: Float,
    val closePrice: Float,
    val volume: String,
    val turnOver: String
) : Parcelable