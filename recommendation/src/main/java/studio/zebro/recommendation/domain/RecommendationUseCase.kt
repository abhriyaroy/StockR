package studio.zebro.recommendation.domain

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import studio.zebro.recommendation.data.HistoricalStockDataRepository
import studio.zebro.recommendation.data.RecommendationRepository
import studio.zebro.recommendation.domain.mapper.HistoricStockDataModelMapper.mapHistoricalStockDataEntityToHistoricStockDataModel
import studio.zebro.recommendation.domain.mapper.StockRecommendationModelMapper.mapStockRecommendationEntityToStockRecommendationModel
import studio.zebro.recommendation.domain.model.HistoricStockDataModel
import studio.zebro.recommendation.domain.model.StockRecommendationModel

interface RecommendationUseCase {
    fun fetchRecommendations(): Flow<List<StockRecommendationModel>>
    fun fetchHistoricData(stockSymbol: String): Flow<HistoricStockDataModel>
}

internal class RecommendationsInteractor(
    private val recommendationRepository: RecommendationRepository,
    private val historicalStockDataRepository: HistoricalStockDataRepository
) : RecommendationUseCase {

    override fun fetchRecommendations(): Flow<List<StockRecommendationModel>> {
        var stockRecommendationModel: StockRecommendationModel? = null
        val stockRecommendationModelTransformedList: MutableList<StockRecommendationModel> =
            mutableListOf()
        var stockRecommendationModelOriginalList: List<StockRecommendationModel> = listOf()
        return recommendationRepository.fetchStockRecommendations()
            .map {
                Log.d(this.javaClass.name, "---->>>>> here 1")
                it.map {
                    mapStockRecommendationEntityToStockRecommendationModel(it)
                }
            }

//            .flatMapConcat {
//                Log.d(this.javaClass.name, "---->>>>> here 2")
//                stockRecommendationModelOriginalList = it
//                flattenListOfStockRecommendationModel(it)
//            }.flatMapConcat {
//                Log.d(this.javaClass.name, "---->>>>> here 3")
//                stockRecommendationModel = it
//                    historicalStockDataRepository.getHistoricDataForStock(it.shortName)
//            }
//            .transform {
//                Log.d(this.javaClass.name, "---->>>>> here $it")
//                stockRecommendationModel!!.historicStockData =
//                    mapHistoricalStockDataEntityToHistoricStockDataModel(it)
//                stockRecommendationModelTransformedList.add(stockRecommendationModel!!)
//                if (stockRecommendationModelTransformedList.size == stockRecommendationModelOriginalList.size) {
//                    emit(stockRecommendationModelTransformedList)
//                }
//            }
            .flowOn(Dispatchers.IO)
    }

    override fun fetchHistoricData(stockSymbol: String): Flow<HistoricStockDataModel> {
        return historicalStockDataRepository.getHistoricDataForStock(stockSymbol)
            .map {
                mapHistoricalStockDataEntityToHistoricStockDataModel(it)
            }
    }
}

private fun flattenListOfStockRecommendationModel(list: List<StockRecommendationModel>) =
    flow {
        list.forEach {
            Log.d(this.javaClass.name, "---->>>>> here emite $it")
            emit(it)
        }
    }



//it.map {
//    ).let { stockRecommendationModel ->
//        historicalStockDataRepository.getHistoricDataForStock(
//            stockRecommendationModel.shortName
//        ).collect {
//            stockRecommendationModel.historicStockData =
//                mapHistoricalStockDataEntityToHistoricStockDataModel(
//                    it
//                )
//        }
//    }
//}