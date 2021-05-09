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
        val newsHeadlines: Elements = doc.select(tableItemCss)
        return mutableListOf<StockRecommendationsDataModel>().let {
           Log.d(this.javaClass.name, newsHeadlines[0].toString() )
            Response.success(it)
        }
    }

}