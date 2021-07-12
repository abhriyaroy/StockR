package studio.zebro.recommendation.data.mapper

import studio.zebro.core.util.CoreUtility
import studio.zebro.datasource.model.HistoricalStockDataDayWiseModel
import studio.zebro.research.data.entity.HistoricalStockDataEntity
import studio.zebro.research.data.entity.HistoricalStockDataIntraDayEntity

object HistoricalStockDataMapperTestHelper {

    fun mapHistoricalStockDataDayWiseModelToHistoricalStockDataEntityTest(list: List<HistoricalStockDataDayWiseModel>): HistoricalStockDataEntity {
        return HistoricalStockDataEntity(
            list.maxOf { it.highPrice.replace(",", "").toFloat() },
            list.minOf { it.lowPrice.replace(",", "").toFloat() },
            list.last().date,
            list.first().date,
            list.reversed()
                .map {
                    mapHistoricalStockDataModelToHistoricalStockDataIntraDayEntityTest(it)
                }
        )
    }

    fun mapHistoricalStockDataModelToHistoricalStockDataIntraDayEntityTest(
        historicalStockDataDayWiseModel: HistoricalStockDataDayWiseModel
    ): HistoricalStockDataIntraDayEntity {
        return HistoricalStockDataIntraDayEntity(
            historicalStockDataDayWiseModel.date,
            historicalStockDataDayWiseModel.stockName,
            historicalStockDataDayWiseModel.stockSeries,
            CoreUtility.replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.openPrice),
            CoreUtility.replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.highPrice),
            CoreUtility.replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.lowPrice),
            CoreUtility.replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.ltpPrice),
            CoreUtility.replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.closePrice),
            historicalStockDataDayWiseModel.volume,
            historicalStockDataDayWiseModel.turnOver
        )
    }
}