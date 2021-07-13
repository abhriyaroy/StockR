package studio.zebro.research.testdataprovider

import studio.zebro.datasource.model.HistoricalStockDataDayWiseModel
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
}