package studio.zebro.recommendation.domain.mapper

import studio.zebro.recommendation.data.entity.HistoricalStockDataEntity
import studio.zebro.recommendation.data.entity.HistoricalStockDataIntraDayEntity
import studio.zebro.recommendation.domain.model.HistoricStockDataDayWiseModel
import studio.zebro.recommendation.domain.model.HistoricStockDataModel

object HistoricStockDataModelMapper {
    fun mapHistoricalStockDataEntityToHistoricStockDataModel(historicalStockDataEntity: HistoricalStockDataEntity): HistoricStockDataModel {
        return HistoricStockDataModel(
            historicalStockDataEntity.dataSetAllTimeHigh,
            historicalStockDataEntity.dataSetAllTimeLow,
            historicalStockDataEntity.dataSetStartDate,
            historicalStockDataEntity.dataSetEndDate,
            historicalStockDataEntity.dataItemsList
                .map {
                    mapHistoricalStockDataIntraDayEntityToHistoricStockDataDayWiseModel(it)
                },
        )
    }

    fun mapHistoricStockDataModelToHistoricalStockDataEntity(historicStockDataModel: HistoricStockDataModel): HistoricalStockDataEntity {
        return HistoricalStockDataEntity(
            historicStockDataModel.dataSetAllTimeHigh,
            historicStockDataModel.dataSetAllTimeLow,
            historicStockDataModel.dataSetStartDate,
            historicStockDataModel.dataSetEndDate,
            historicStockDataModel.dataItemsList
                .map {
                    mapHistoricStockDataDayWiseModelToHistoricalStockDataIntraDayEntity(it)
                },
        )
    }

    fun mapHistoricalStockDataIntraDayEntityToHistoricStockDataDayWiseModel(historicalStockDataIntraDayEntity : HistoricalStockDataIntraDayEntity): HistoricStockDataDayWiseModel{
        return HistoricStockDataDayWiseModel(
            historicalStockDataIntraDayEntity.date,
            historicalStockDataIntraDayEntity.stockName,
            historicalStockDataIntraDayEntity.stockSeries,
            historicalStockDataIntraDayEntity.openPrice,
            historicalStockDataIntraDayEntity.highPrice,
            historicalStockDataIntraDayEntity.lowPrice,
            historicalStockDataIntraDayEntity.ltpPrice,
            historicalStockDataIntraDayEntity.closePrice,
            historicalStockDataIntraDayEntity.volume,
            historicalStockDataIntraDayEntity.turnOver,
        )
    }

    fun mapHistoricStockDataDayWiseModelToHistoricalStockDataIntraDayEntity(historicStockDataDayWiseModel : HistoricStockDataDayWiseModel): HistoricalStockDataIntraDayEntity{
        return HistoricalStockDataIntraDayEntity(
            historicStockDataDayWiseModel.date,
            historicStockDataDayWiseModel.stockName,
            historicStockDataDayWiseModel.stockSeries,
            historicStockDataDayWiseModel.openPrice,
            historicStockDataDayWiseModel.highPrice,
            historicStockDataDayWiseModel.lowPrice,
            historicStockDataDayWiseModel.ltpPrice,
            historicStockDataDayWiseModel.closePrice,
            historicStockDataDayWiseModel.volume,
            historicStockDataDayWiseModel.turnOver,
        )
    }
}