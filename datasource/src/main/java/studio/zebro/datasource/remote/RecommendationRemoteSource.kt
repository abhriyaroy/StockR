package studio.zebro.datasource.remote

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.Response
import studio.zebro.datasource.BuildConfig
import studio.zebro.datasource.model.StockRecommendationsDataModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

interface RecommendationRemoteSource {
    fun getRecommendationsFromKotakSecurities() : Response<List<StockRecommendationsDataModel>>
}

class RecommendationRemoteSourceImpl : RecommendationRemoteSource {

    private val tableItemCss = "td"

    override fun getRecommendationsFromKotakSecurities(): Response<List<StockRecommendationsDataModel>> {


        val oracle = URL("https://www1.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=PIDILITIND&series=EQ&fromDate=undefined&toDate=undefined&datePeriod=3months")
        val `in` = BufferedReader(
            InputStreamReader(oracle.openStream())
        )

        var inputLine: String?
        var outputString = ""
        while (`in`.readLine().also { inputLine = it } != null) outputString+=inputLine
        `in`.close()

        val doc1: Document =
            Jsoup.parse(outputString)

        System.out.println(doc1)
        System.out.println(doc1.select(tableItemCss))


        val doc: Document = Jsoup.connect(BuildConfig.KOTAK_RECOMMENDATIONS_URL).get()
        val scaredRecommendations: Elements = doc.select(tableItemCss)
        return mutableListOf<StockRecommendationsDataModel>().let {
            var count = 0
            while(count<scaredRecommendations.size){
                Log.d(this.javaClass.name, "the item is--> ${scaredRecommendations[count].text()}")
                it.add(
                    StockRecommendationsDataModel(
                        scaredRecommendations[count].text().trim(),
                        scaredRecommendations[++count].text().split(",")[0],
                        scaredRecommendations[count].text().split(",")[1].trim(),
                        scaredRecommendations[++count].text().trim(),
                        scaredRecommendations[++count].text().trim(),
                        scaredRecommendations[++count].text().trim(),
                        scaredRecommendations[++count].text().trim(),
                        scaredRecommendations[++count].text().trim(),
                        scaredRecommendations[++count].text().trim(),
                        scaredRecommendations[++count].text().trim(),
                        scaredRecommendations[++count].text().trim(),
                        scaredRecommendations[++count].text().trim(),
                    )
                )
                count ++
            }
            Response.success(it)
        }
    }

}