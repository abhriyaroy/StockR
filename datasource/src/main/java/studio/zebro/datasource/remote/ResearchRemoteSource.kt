package studio.zebro.datasource.remote

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.Response
import studio.zebro.datasource.BuildConfig
import studio.zebro.datasource.model.NiftyIndexesDayWiseDataModel
import studio.zebro.datasource.model.StockResearchDataModel
import studio.zebro.datasource.util.Constants.CHANGE_PRICE_CLASS_NAME
import studio.zebro.datasource.util.Constants.CURRENT_PRICE_CLASS_NAME
import studio.zebro.datasource.util.Constants.DIV_ITEM_CSS_TAG
import studio.zebro.datasource.util.Constants.ERROR_CODE_NOT_LOADED
import studio.zebro.datasource.util.Constants.NIFTY_50_INDEX_NAME
import studio.zebro.datasource.util.Constants.PLUS_SIGN
import studio.zebro.datasource.util.Constants.TABLE_ITEM_CSS_TAG

interface ResearchRemoteSource {
    fun getNifty50Index(): Response<NiftyIndexesDayWiseDataModel>
    fun getResearchFromKotakSecurities(): Response<List<StockResearchDataModel>>
}

class ResearchRemoteSourceImpl : ResearchRemoteSource {

    override fun getNifty50Index(): Response<NiftyIndexesDayWiseDataModel> {
        val webPageData: Document = Jsoup.connect(BuildConfig.NSE_BASE_URL).get()
        println(webPageData)
        val rawDivItems: Elements = webPageData.select(DIV_ITEM_CSS_TAG)
        val rawNiftyIndicesList = rawDivItems.filter {
            it.className() == CURRENT_PRICE_CLASS_NAME || it.className() == CHANGE_PRICE_CLASS_NAME
        }
        val trimmedChangeStringArray = rawNiftyIndicesList[1].text().trim().split(" ")
        return if (rawNiftyIndicesList.size >= 2) {
            Response.success(
                NiftyIndexesDayWiseDataModel(
                    NIFTY_50_INDEX_NAME,
                    rawNiftyIndicesList[0].text().toFloat(),
                    trimmedChangeStringArray[0],
                    trimmedChangeStringArray[1],
                    trimmedChangeStringArray[0].toCharArray()[0] == PLUS_SIGN
                )
            )
        } else {
            Response.error(ERROR_CODE_NOT_LOADED, ResponseBody.create(null, ""))
        }
    }

    override fun getResearchFromKotakSecurities(): Response<List<StockResearchDataModel>> {
        val parsedData: Document = Jsoup.connect(BuildConfig.KOTAK_RESEARCH_URL).get()
        val rawStockResearch: Elements = parsedData.select(TABLE_ITEM_CSS_TAG)
        val iterableSize = rawStockResearch.size
        return mutableListOf<StockResearchDataModel>().let {
            var count = 0
            while (count < iterableSize) {
                rawStockResearch
                if (
                    rawStockResearch[count].text().trim().equals("NA", true)
                    || rawStockResearch[count + 1].text().split(",")[0].equals("NA", true)
                    || rawStockResearch[count + 1].text().split(",")[1].trim()
                        .equals("NA", true)
                    || rawStockResearch[count + 2].text().trim().equals("NA", true)
                    || rawStockResearch[count + 3].text().trim().equals("NA", true)
                    || rawStockResearch[count + 4].text().trim().equals("NA", true)
                    || rawStockResearch[count + 5].text().trim().equals("NA", true)
                    || rawStockResearch[count + 6].text().trim().equals("NA", true)
                    || rawStockResearch[count + 7].text().trim().equals("NA", true)
                    || rawStockResearch[count + 8].text().trim().equals("NA", true)
                    || rawStockResearch[count + 9].text().trim().equals("NA", true)
                    || rawStockResearch[count + 10].text().trim().equals("NA", true)
                ) {
                    count += 10
                } else {
                    it.add(
                        StockResearchDataModel(
                            rawStockResearch[count].text().trim(),
                            rawStockResearch[++count].text().split(",")[0],
                            rawStockResearch[count].text().split(",")[1].trim(),
                            rawStockResearch[++count].text().trim(),
                            rawStockResearch[++count].text().trim(),
                            rawStockResearch[++count].text().trim(),
                            rawStockResearch[++count].text().trim(),
                            rawStockResearch[++count].text().trim(),
                            rawStockResearch[++count].text().trim(),
                            rawStockResearch[++count].text().trim(),
                            rawStockResearch[++count].text().trim(),
                            rawStockResearch[++count].text().trim(),
                        )
                    )
                }
                count++
            }
            Response.success(it)
        }
    }

}