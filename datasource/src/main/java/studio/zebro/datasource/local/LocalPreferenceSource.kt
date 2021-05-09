package studio.zebro.datasource.local

import com.chibatching.kotpref.KotprefModel
import com.google.gson.Gson
import java.lang.reflect.Type
import com.google.gson.reflect.TypeToken
import studio.zebro.datasource.model.StockRecommendationsDataModel

interface LocalPreferenceSource {
    suspend fun getSavedKotakStockRecommendation(): List<StockRecommendationsDataModel>
    suspend fun saveStockKotakRecommendation(stockRecommendationsDataModel: List<StockRecommendationsDataModel>)
}

class LocalPreferenceSourceImpl(private val gson: Gson) : LocalPreferenceSource {
    private val stockRecommendationsDataModelAdapter: Type =
    object : TypeToken<ArrayList<StockRecommendationsDataModel>>(){}.type


    override suspend fun getSavedKotakStockRecommendation(): List<StockRecommendationsDataModel> {
        return Preferences.kotakStockRecommendations.let {
            if(it.isNullOrEmpty()){
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

    private object Preferences : KotprefModel() {
        var kotakStockRecommendations by stringPref()
    }
}