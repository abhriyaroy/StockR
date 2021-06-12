package studio.zebro.datasource.model

data class NiftyIndexesDayModel(
    val name: String,
    val value: Float,
    val changePercentage: String,
    val isPositiveChange: Boolean
)