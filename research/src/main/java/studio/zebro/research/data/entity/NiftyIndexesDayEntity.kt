package studio.zebro.research.data.entity

data class NiftyIndexesDayEntity (
    val name: String,
    val value: Float,
    val changePercentage: String,
    val changeValue: String,
    val isPositiveChange: Boolean
)