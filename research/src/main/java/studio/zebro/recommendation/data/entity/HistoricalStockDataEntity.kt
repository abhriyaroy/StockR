package studio.zebro.recommendation.data.entity

data class HistoricalStockDataEntity(
    val dataSetAllTimeHigh : Float,
    val dataSetAllTimeLow : Float,
    val dataSetStartDate : String,
    val dataSetEndDate : String,
    val dataItemsList : List<HistoricalStockDataIntraDayEntity>
)

data class HistoricalStockDataIntraDayEntity(
    val date: String,
    val stockName: String,
    val stockSeries: String,
    val openPrice: Float,
    val highPrice: Float,
    val lowPrice: Float,
    val ltpPrice: Float,
    val closePrice: Float,
    val volume: String,
    val turnOver: String
)