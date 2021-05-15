package studio.zebro.datasource.remote

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.Response
import studio.zebro.datasource.BuildConfig
import studio.zebro.datasource.model.StockRecommendationsDataModel
import studio.zebro.datasource.util.Constants.TABLE_ITEM_CSS_TAG

interface RecommendationRemoteSource {
    fun getRecommendationsFromKotakSecurities() : Response<List<StockRecommendationsDataModel>>
}

class RecommendationRemoteSourceImpl : RecommendationRemoteSource {


    override fun getRecommendationsFromKotakSecurities(): Response<List<StockRecommendationsDataModel>> {
        val parsedData: Document = Jsoup.connect(BuildConfig.KOTAK_RECOMMENDATIONS_URL).get()
        val rawStockRecommendations: Elements = parsedData.select(TABLE_ITEM_CSS_TAG)
        return mutableListOf<StockRecommendationsDataModel>().let {
            var count = 0
            while(count<rawStockRecommendations.size){
                Log.d(this.javaClass.name, "the item is--> ${rawStockRecommendations[count].text()}")
                it.add(
                    StockRecommendationsDataModel(
                        rawStockRecommendations[count].text().trim(),
                        rawStockRecommendations[++count].text().split(",")[0],
                        rawStockRecommendations[count].text().split(",")[1].trim(),
                        rawStockRecommendations[++count].text().trim(),
                        rawStockRecommendations[++count].text().trim(),
                        rawStockRecommendations[++count].text().trim(),
                        rawStockRecommendations[++count].text().trim(),
                        rawStockRecommendations[++count].text().trim(),
                        rawStockRecommendations[++count].text().trim(),
                        rawStockRecommendations[++count].text().trim(),
                        rawStockRecommendations[++count].text().trim(),
                        rawStockRecommendations[++count].text().trim(),
                    )
                )
                count ++
            }
            Response.success(it)
        }
    }

}