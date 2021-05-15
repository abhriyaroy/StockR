package studio.zebro.datasource.model

data class HistoricalStockDataDayWiseModel(
    val date: String,
    val stockName: String,
    val stockSeries: String,
    val openPrice: String,
    val highPrice: String,
    val lowPrice: String,
    val ltpPrice: String,
    val closePrice: String,
    val volume: String,
    val turnOver: String
)