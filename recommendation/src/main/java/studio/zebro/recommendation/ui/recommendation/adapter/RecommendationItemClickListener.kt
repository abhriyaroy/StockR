package studio.zebro.recommendation.ui.recommendation.adapter

import androidx.constraintlayout.widget.ConstraintLayout
import studio.zebro.recommendation.domain.model.StockRecommendationModel
import studio.zebro.recommendation.ui.transition.RecommendationItemToRecommendationDetailTransitionModel

interface RecommendationItemClickListener {
    fun onRecommendationItemClick(
        position: Int,
        stockRecommendationModel: StockRecommendationModel,
        transitionModel : RecommendationItemToRecommendationDetailTransitionModel
    )
}