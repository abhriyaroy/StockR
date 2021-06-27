package studio.zebro.research.domain.model

data class NiftyIndexesDayModel (
    val name: String,
    val value: Float,
    val changePercentage: String,
    val changeValue: String,
    val isPositiveChange: Boolean
)