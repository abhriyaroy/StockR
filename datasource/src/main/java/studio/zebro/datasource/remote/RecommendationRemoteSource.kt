package studio.zebro.datasource.remote

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.Response
import studio.zebro.datasource.BuildConfig
import studio.zebro.datasource.model.NiftyIndexesDayModel
import studio.zebro.datasource.model.StockRecommendationsDataModel
import studio.zebro.datasource.util.Constants.DIV_ITEM_CSS_TAG
import studio.zebro.datasource.util.Constants.TABLE_ITEM_CSS_TAG

interface RecommendationRemoteSource {
    fun getNifty50Index() : Response<NiftyIndexesDayModel>?
    fun getRecommendationsFromKotakSecurities(): Response<List<StockRecommendationsDataModel>>
}

class RecommendationRemoteSourceImpl : RecommendationRemoteSource {

    override fun getNifty50Index(): Response<NiftyIndexesDayModel>? {
//        val webPageData: Document = Jsoup.connect(BuildConfig.NSE_BASE_URL).get()
//        println(webPageData)
//        val rawDivItems: Elements = webPageData.select(DIV_ITEM_CSS_TAG)
//        val rawNiftyIndicesList = rawDivItems.filter {
//           it.className() == "current-price" || it.className() == "change-live mt25"
//        }
//        return if(rawNiftyIndicesList.size>=2){
//            Response.success(NiftyIndexesDayModel(
//                "Nifty 50 Index",
//                rawNiftyIndicesList[0].text().toFloat(),
//                rawNiftyIndicesList[1].text().trim().toFloat(),
//
//            ))
//        } else {
//            null
//        }
        return null
    }

    override fun getRecommendationsFromKotakSecurities(): Response<List<StockRecommendationsDataModel>> {
        val parsedData: Document = Jsoup.connect(BuildConfig.KOTAK_RECOMMENDATIONS_URL).get()
        val rawStockRecommendations: Elements = parsedData.select(TABLE_ITEM_CSS_TAG)
        Log.d(this.javaClass.name, "The fetched items are $rawStockRecommendations")
        var iterableSize = rawStockRecommendations.size
        return mutableListOf<StockRecommendationsDataModel>().let {
            var count = 0
            while (count < iterableSize) {
                rawStockRecommendations
                if (
                    rawStockRecommendations[count].text().trim().equals("NA", true)
                    || rawStockRecommendations[count + 1].text().split(",")[0].equals("NA", true)
                    || rawStockRecommendations[count + 1].text().split(",")[1].trim()
                        .equals("NA", true)
                    || rawStockRecommendations[count + 2].text().trim().equals("NA", true)
                    || rawStockRecommendations[count + 3].text().trim().equals("NA", true)
                    || rawStockRecommendations[count + 4].text().trim().equals("NA", true)
                    || rawStockRecommendations[count + 5].text().trim().equals("NA", true)
                    || rawStockRecommendations[count + 6].text().trim().equals("NA", true)
                    || rawStockRecommendations[count + 7].text().trim().equals("NA", true)
                    || rawStockRecommendations[count + 8].text().trim().equals("NA", true)
                    || rawStockRecommendations[count + 9].text().trim().equals("NA", true)
                    || rawStockRecommendations[count + 10].text().trim().equals("NA", true)
                ) {
                    count += 10
                } else {
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
                }
                count++
            }
            Response.success(it)
        }
    }

}