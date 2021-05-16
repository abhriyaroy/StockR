package studio.zebro.datasource.remote

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.Response
import studio.zebro.datasource.model.HistoricalStockDataDayWiseModel
import studio.zebro.datasource.util.Constants.TABLE_ITEM_CSS_TAG
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLEncoder

interface HistoricalDataRemoteSource {
    fun get3monthsHistoricData(stockSymbol: String): Response<List<HistoricalStockDataDayWiseModel>>
}


class HistoricalDataRemoteSourceImpl : HistoricalDataRemoteSource {

    override fun get3monthsHistoricData(stockSymbol: String): Response<List<HistoricalStockDataDayWiseModel>> {
        val oracle =
            URL(
                "https://www1.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=${
                    URLEncoder.encode(
                        stockSymbol
                    )
                }&series=EQ&fromDate=undefined&toDate=undefined&datePeriod=3months"
            )
        val `in` = BufferedReader(
            InputStreamReader(oracle.openStream())
        )
        var inputLine: String?
        var outputString = ""
        while (`in`.readLine().also { inputLine = it } != null) {
            Log.d(this.javaClass.name, "---->>>>> obtained $inputLine ")
            outputString += inputLine
        }
        `in`.close()
        val parsedData: Document = Jsoup.parse(outputString)
        val historicalStockRawData = parsedData.select(TABLE_ITEM_CSS_TAG)
        return mutableListOf<HistoricalStockDataDayWiseModel>().let {
            var count = 0
            while (count < historicalStockRawData.size) {
                it.add(
                    HistoricalStockDataDayWiseModel(
                        historicalStockRawData[count].text().trim(),
                        historicalStockRawData[++count].text().trim(),
                        historicalStockRawData[++count].text().trim(),
                        historicalStockRawData[++count].text().trim(),
                        historicalStockRawData[++count].text().trim(),
                        historicalStockRawData[++count].text().trim(),
                        historicalStockRawData[++count].text().trim(),
                        historicalStockRawData[++count].text().trim(),
                        historicalStockRawData[++count].text().trim(),
                        historicalStockRawData[++count].text().trim(),
                    )
                )
                count++
            }
            Log.d(this.javaClass.name, "---->>>>> completed $it ")
            Response.success(it)
        }
    }

}