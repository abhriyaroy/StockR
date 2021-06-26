package studio.zebro.datasource.local

import com.chibatching.kotpref.KotprefModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import studio.zebro.datasource.model.HistoricalStockDataDayWiseModel
import studio.zebro.datasource.model.StockResearchsDataModel
import java.lang.reflect.Type

interface LocalPreferenceSource {
    suspend fun getSavedKotakStockResearch(): List<StockResearchsDataModel>
    suspend fun saveStockKotakResearch(stockResearchsDataModel: List<StockResearchsDataModel>)
    suspend fun saveStockHistoricalData(
        stockName: String,
        historicalData: List<HistoricalStockDataDayWiseModel>
    )

    suspend fun getSavedStockHistoricalData(stockName: String): List<HistoricalStockDataDayWiseModel>
}

class LocalPreferenceSourceImpl(private val gson: Gson) : LocalPreferenceSource {
    private val stockResearchsDataModelAdapter: Type =
        object : TypeToken<ArrayList<StockResearchsDataModel>>() {}.type

    private val historicStockDataDayWiseModelAdapter: Type =
        object : TypeToken<ArrayList<HistoricalStockDataDayWiseModel>>() {}.type


    override suspend fun getSavedKotakStockResearch(): List<StockResearchsDataModel> {
        return Preferences.kotakStockResearchs.let {
            if (it.isNullOrEmpty()) {
                listOf()
            } else {
                gson.fromJson(it, stockResearchsDataModelAdapter)
            }
        }
    }

    override suspend fun saveStockKotakResearch(stockResearchsDataModel: List<StockResearchsDataModel>) {
        Preferences.kotakStockResearchs =
            gson.toJson(stockResearchsDataModel, stockResearchsDataModelAdapter)
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
        var kotakStockResearchs by stringPref()
    }
}