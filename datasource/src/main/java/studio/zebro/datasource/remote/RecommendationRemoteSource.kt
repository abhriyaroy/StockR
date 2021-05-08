package studio.zebro.datasource.remote

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import studio.zebro.datasource.model.StockRecommendationsModel


interface RecommendationRemoteSource {
    fun getRecommendationsFromKotakSecurities() : List<StockRecommendationsModel>
}

class RecommendationRemoteSourceImpl : RecommendationRemoteSource {
    override fun getRecommendationsFromKotakSecurities(): List<StockRecommendationsModel> {
        val doc: Document = Jsoup.connect("https://bestbrokerindia.com/kotak-securities-share-broker-market-tips.html").get()
        val newsHeadlines: Elements = doc.select("td")
        return mutableListOf<StockRecommendationsModel>().let {
           Log.d(this.javaClass.name, newsHeadlines[0].toString() )
            it
        }
    }

}