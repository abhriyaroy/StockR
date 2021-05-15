package studio.zebro.datasource.local

import com.chibatching.kotpref.KotprefModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import studio.zebro.datasource.model.HistoricalStockDataDayWiseModel
import studio.zebro.datasource.model.StockRecommendationsDataModel
import java.lang.reflect.Type

interface LocalPreferenceSource {
    suspend fun getSavedKotakStockRecommendation(): List<StockRecommendationsDataModel>
    suspend fun saveStockKotakRecommendation(stockRecommendationsDataModel: List<StockRecommendationsDataModel>)
    suspend fun saveStockHistoricalData(
        stockName: String,
        historicalData: List<HistoricalStockDataDayWiseModel>
    )

    suspend fun getSavedStockHistoricalData(stockName: String): List<HistoricalStockDataDayWiseModel>
}

class LocalPreferenceSourceImpl(private val gson: Gson) : LocalPreferenceSource {
    private val stockRecommendationsDataModelAdapter: Type =
        object : TypeToken<ArrayList<StockRecommendationsDataModel>>() {}.type

    private val historicStockDataDayWiseModelAdapter: Type =
        object : TypeToken<ArrayList<HistoricalStockDataDayWiseModel>>() {}.type


    override suspend fun getSavedKotakStockRecommendation(): List<StockRecommendationsDataModel> {
        return Preferences.kotakStockRecommendations.let {
            if (it.isNullOrEmpty()) {
                listOf()
            } else {
                gson.fromJson(it, stockRecommendationsDataModelAdapter)
            }
        }
    }

    override suspend fun saveStockKotakRecommendation(stockRecommendationsDataModel: List<StockRecommendationsDataModel>) {
        Preferences.kotakStockRecommendations =
            gson.toJson(stockRecommendationsDataModel, stockRecommendationsDataModelAdapter)
    }

    override suspend fun getSavedStockHistoricalData(stockName: String): List<HistoricalStockDataDayWiseModel> {
        return Preferences.preferences.getString(stockName, "")
            .let {
                if (it.isNullOrEmpty()) {
                    listOf()
                } else {
                    gson.fromJson(it, historicStockDataDayWiseModelAdapter)
                }
            }
    }

    override suspend fun saveStockHistoricalData(
        stockName: String,
        historicalData: List<HistoricalStockDataDayWiseModel>
    ) {
        with(Preferences.preferences.edit()) {
            putString(stockName, gson.toJson(historicalData, historicStockDataDayWiseModelAdapter))
            commit()
        }
    }

    private object Preferences : KotprefModel() {
        var kotakStockRecommendations by stringPref()
    }
}