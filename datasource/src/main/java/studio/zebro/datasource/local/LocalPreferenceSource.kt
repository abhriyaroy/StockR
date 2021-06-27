package studio.zebro.datasource.local

import com.chibatching.kotpref.KotprefModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import studio.zebro.datasource.model.HistoricalStockDataDayWiseModel
import studio.zebro.datasource.model.StockResearchDataModel
import java.lang.reflect.Type

interface LocalPreferenceSource {
    suspend fun getSavedKotakStockResearch(): List<StockResearchDataModel>
    suspend fun saveStockKotakResearch(stockResearchDataModel: List<StockResearchDataModel>)
    suspend fun saveStockHistoricalData(
        stockName: String,
        historicalData: List<HistoricalStockDataDayWiseModel>
    )

    suspend fun getSavedStockHistoricalData(stockName: String): List<HistoricalStockDataDayWiseModel>
}

class LocalPreferenceSourceImpl(private val gson: Gson) : LocalPreferenceSource {
    private val stockResearchDataModelAdapter: Type =
        object : TypeToken<ArrayList<StockResearchDataModel>>() {}.type

    private val historicStockDataDayWiseModelAdapter: Type =
        object : TypeToken<ArrayList<HistoricalStockDataDayWiseModel>>() {}.type


    override suspend fun getSavedKotakStockResearch(): List<StockResearchDataModel> {
        return Preferences.kotakStockResearch.let {
            if (it.isNullOrEmpty()) {
                listOf()
            } else {
                gson.fromJson(it, stockResearchDataModelAdapter)
            }
        }
    }

    override suspend fun saveStockKotakResearch(stockResearchDataModel: List<StockResearchDataModel>) {
        Preferences.kotakStockResearch =
            gson.toJson(stockResearchDataModel, stockResearchDataModelAdapter)
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
        var kotakStockResearch by stringPref()
    }
}