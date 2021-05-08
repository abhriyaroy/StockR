package studio.zebro.datasource.remote

import org.jsoup.Jsoup




interface RecommendationRemoteSource {
    fun getRecommendationsFromKotakSecurities() :
}

class RecommendationRemoteSourceImpl : RecommendationRemoteSource {
    override fun getRecommendationsFromKotakSecurities(): ??? {
        val doc: Document = Jsoup.connect("https://en.wikipedia.org/").get()
        log(doc.title())
        val newsHeadlines: Elements = doc.select("#mp-itn b a")
        for (headline in newsHeadlines) {
            log(
                "%s\n\t%s",
                headline.attr("title"), headline.absUrl("href")
            )
        }
    }

}