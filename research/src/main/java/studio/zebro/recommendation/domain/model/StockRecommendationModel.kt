package studio.zebro.recommendation.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StockRecommendationModel(
    val fullName: String,
    val shortName: String,
    val codeNumber: String,
    val updatedAt: String,
    val entryPriceInRupees: String,
    val targetPriceInRupees: String,
    val stopLossPriceInRupees: String,
    val upsidePercentage: String,
    val duration: String,
    val type: String,
    val action: String,
    val remark: String,
    var historicalData : HistoricStockDataModel?=null
) : Parcelable