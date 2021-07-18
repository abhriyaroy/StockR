package studio.zebro.research.testdataprovider

import studio.zebro.datasource.model.HistoricalStockDataDayWiseModel
import studio.zebro.research.data.entity.HistoricalStockDataEntity
import studio.zebro.research.data.entity.HistoricalStockDataIntraDayEntity
import studio.zebro.research.data.entity.StockResearchEntity
import java.util.*

object HistoricalStockDataProvider {
    fun getHistoricalStockDataDayWiseModel() = HistoricalStockDataDayWiseModel(
       UUID.randomUUID().toString(),
       UUID.randomUUID().toString(),
       UUID.randomUUID().toString(),
       Random().nextFloat().toString().replace('.', ','),
       Random().nextFloat().toString().replace('.', ','),
       Random().nextFloat().toString().replace('.', ','),
       Random().nextFloat().toString().replace('.', ','),
       Random().nextFloat().toString().replace('.', ','),
       Random().nextFloat().toString().replace('.', ','),
       Random().nextFloat().toString().replace('.', ','),
    )

    fun getHistoricalStockDataDayWiseModelList() = listOf(
        getHistoricalStockDataDayWiseModel(),
        getHistoricalStockDataDayWiseModel(),
        getHistoricalStockDataDayWiseModel(),
        getHistoricalStockDataDayWiseModel()
    )

    fun getHistoricalStockDataIntraDayEntity() = HistoricalStockDataIntraDayEntity(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        Random().nextFloat(),
        Random().nextFloat(),
        Random().nextFloat(),
        Random().nextFloat(),
        Random().nextFloat(),
        Random().nextFloat().toString(),
        Random().nextFloat().toString(),
    )

    fun getHistoricalStockDataEntity() = HistoricalStockDataEntity(
        Random().nextFloat(),
        Random().nextFloat(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        listOf(
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity(),
            getHistoricalStockDataIntraDayEntity()
        )
    )
}