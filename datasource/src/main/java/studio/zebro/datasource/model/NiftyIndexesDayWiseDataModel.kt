package studio.zebro.datasource.model

data class NiftyIndexesDayWiseDataModel(
    val name: String,
    val value: Float,
    val changePercentage: String,
    val changeValue: String,
    val isPositiveChange: Boolean
)