package studio.zebro.recommendation.data.mapper

import studio.zebro.core.util.CoreUtility.replaceDelimiterFromNumberIfAnyStringAndReturnFloat
import studio.zebro.datasource.model.HistoricalStockDataDayWiseModel
import studio.zebro.recommendation.data.entity.HistoricalStockDataEntity
import studio.zebro.recommendation.data.entity.HistoricalStockDataIntraDayEntity

object HistoricalStockDataMapper {

    fun mapHistoricalStockDataEntityToHistoricalStockDataDayWiseModel(historicalStockDataEntity: HistoricalStockDataEntity): List<HistoricalStockDataDayWiseModel> {
        return historicalStockDataEntity.dataItemsList
            .map {
                mapHistoricalStockDataIntraDayEntityToHistoricalStockDataModel(it)
            }
    }

    fun mapHistoricalStockDataEntityToHistoricalStockDataDayWiseModel(list: List<HistoricalStockDataDayWiseModel>): HistoricalStockDataEntity {
        return HistoricalStockDataEntity(
            list.maxOf { replaceDelimiterFromNumberIfAnyStringAndReturnFloat(it.highPrice) },
            list.minOf { replaceDelimiterFromNumberIfAnyStringAndReturnFloat(it.lowPrice) },
            list.last().date,
            list.first().date,
            list.map {
                mapHistoricalStockDataModelToHistoricalStockDataIntraDayEntity(it)
            }
        )
    }

    fun mapHistoricalStockDataIntraDayEntityToHistoricalStockDataModel(
        historicalStockDataRangeEntity: HistoricalStockDataIntraDayEntity
    ): HistoricalStockDataDayWiseModel {
        return HistoricalStockDataDayWiseModel(
            historicalStockDataRangeEntity.date,
            historicalStockDataRangeEntity.stockName,
            historicalStockDataRangeEntity.stockSeries,
            historicalStockDataRangeEntity.openPrice.toString(),
            historicalStockDataRangeEntity.highPrice.toString(),
            historicalStockDataRangeEntity.lowPrice.toString(),
            historicalStockDataRangeEntity.ltpPrice.toString(),
            historicalStockDataRangeEntity.closePrice.toString(),
            historicalStockDataRangeEntity.volume,
            historicalStockDataRangeEntity.turnOver
        )
    }

    fun mapHistoricalStockDataModelToHistoricalStockDataIntraDayEntity(
        historicalStockDataDayWiseModel: HistoricalStockDataDayWiseModel
    ): HistoricalStockDataIntraDayEntity {
        return HistoricalStockDataIntraDayEntity(
            historicalStockDataDayWiseModel.date,
            historicalStockDataDayWiseModel.stockName,
            historicalStockDataDayWiseModel.stockSeries,
            replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.openPrice),
            replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.highPrice),
            replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.lowPrice),
            replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.ltpPrice),
            replaceDelimiterFromNumberIfAnyStringAndReturnFloat(historicalStockDataDayWiseModel.closePrice),
            historicalStockDataDayWiseModel.volume,
            historicalStockDataDayWiseModel.turnOver
        )
    }

}