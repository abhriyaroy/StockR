package studio.zebro.research.data.entity

data class StockResearchEntity(
    val fullName: String,
    val shortName: String,
    val codeNumber: String,
    val updatedAt: String,
    val entryPriceInRupees: String,
    val targetPriceInRupees: String,
    val stopLossPriceInRupees: String,
    val upsidePercentage: String,
    val duration: String,
    val type: String,
    val action: String,
    val remark: String
)