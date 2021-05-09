package studio.zebro.datasource.remote

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.Response
import studio.zebro.datasource.BuildConfig
import studio.zebro.datasource.model.StockRecommendationsDataModel

interface RecommendationRemoteSource {
    fun getRecommendationsFromKotakSecurities() : Response<List<StockRecommendationsDataModel>>
}

class RecommendationRemoteSourceImpl : RecommendationRemoteSource {

    private val tableItemCss = "td"

    override fun getRecommendationsFromKotakSecurities(): Response<List<StockRecommendationsDataModel>> {
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